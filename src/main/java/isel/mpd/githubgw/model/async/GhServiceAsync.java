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
import isel.mpd.githubgw.model.streams.ContributorsLazyStream;
import isel.mpd.githubgw.webapi.GhApi;
import isel.mpd.githubgw.webapi.dto.GhOrgDto;
import isel.mpd.githubgw.webapi.dto.GhRepoDto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by Miguel Gamboa on 08-06-2015.
 */
public class GhServiceAsync implements AutoCloseable {

    public final GhApi gh;
    public HashMap<IGhOrg, Set<IGhUser>> identities;

    public GhServiceAsync() {
        this(new GhApi());
    }

    public GhServiceAsync(GhApi ghApi) {
        this.gh = ghApi;
        this.identities = new HashMap<>();
    }

    public CompletableFuture<IGhOrg> getOrg(String login) throws ExecutionException, InterruptedException {
        return this.gh.getOrg(login).thenApplyAsync((GhOrgDto dto) -> {
            IGhOrg org = new GhOrg(dto, getRepos(dto.id));
            this.identities.put(org, new HashSet<>());

            return org;
        });
    }

    public CompletableFuture<Stream<IGhRepo>> getRepos(int id) {
        return CompletableFuture.supplyAsync(() -> {
            Iterable<IGhRepo> i = new ContributorsLazyStream<>(this, id, gh.getOrgRepos(id));
            return StreamSupport.stream((i.spliterator()), false);
        });
    }

    /*
    this.gh.getOrgRepos(id, 1).thenApplyAsync((list) -> list.stream()
                .map((dto) -> {
                    IGhOrg org = this.identities.keySet()
                            .stream()
                            .filter((o) -> o.getId() == id)
                            .findFirst()
                            .orElse(null);
                    return new GhRepo(dto, org, null);
                }));
     */

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
