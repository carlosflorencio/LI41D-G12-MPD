/*
 * Copyright (C) 2015 Miguel Gamboa at CCISEL
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package isel.mpd.githubgw.model.async;

import isel.mpd.githubgw.model.IGhOrg;
import isel.mpd.githubgw.model.IGhRepo;
import isel.mpd.githubgw.model.IGhUser;
import isel.mpd.githubgw.webapi.GhApi;
import isel.mpd.githubgw.webapi.dto.GhOrgDto;
import isel.mpd.githubgw.webapi.dto.GhRepoDto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * Created by Miguel Gamboa on 08-06-2015.
 */
public class GhServiceAsync implements AutoCloseable {

    private final GhApi gh;
    private HashMap<IGhOrg, Set<IGhUser>> identities;
    private static ExecutorService executor = Executors.newFixedThreadPool(3);

    public GhServiceAsync() {
        this(new GhApi());
    }

    public GhServiceAsync(GhApi ghApi) {
        this.gh = ghApi;
        this.identities = new HashMap<>();
    }

    public CompletableFuture<IGhOrg> getOrg(String login) throws ExecutionException, InterruptedException {
        return this.gh.getOrg(login).thenApplyAsync((GhOrgDto dto) -> {
            CompletableFuture<Stream<IGhRepo>> future = new CompletableFuture<>();
            IGhOrg org = new GhOrg(dto, future);
            this.identities.put(org, new HashSet<>());

            executor.submit(() -> {
                try {
                    future.complete(getRepos(org).get());
                } catch (InterruptedException | ExecutionException e) {
                    future.completeExceptionally(e);
                }
            });

            return org;
        });
    }

    public CompletableFuture<Stream<IGhRepo>> getRepos(IGhOrg org) {
        return this.gh.getOrgRepos(org.getId(), 1).thenApplyAsync((list) -> list.stream()
                .map((dto) -> new GhRepo(dto, org, null)));
    }

    private static Future<Stream<IGhUser>> getContributorsOfRepo(GhRepoDto s) {
        return null;
    }


    @Override
    public void close() throws Exception {
        if (!gh.isClosed())
            gh.close();
    }

    public boolean isClosed() {
        return gh.isClosed();
    }

}
