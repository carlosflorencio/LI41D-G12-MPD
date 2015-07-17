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
import isel.mpd.githubgw.model.IGhUser;
import isel.mpd.githubgw.model.streams.ContributorsLazyStream;
import isel.mpd.githubgw.webapi.GhApi;
import isel.mpd.githubgw.webapi.dto.GhOrgDto;
import isel.mpd.githubgw.webapi.dto.GhUserDto;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class GhServiceAsync implements AutoCloseable {

    private static GhServiceAsync instance;

    public final GhApi gh;
    private HashMap<IGhUser, Set<IGhOrg>> identities;
    private Set<IGhOrg> orgs;


    public GhServiceAsync() {
        this(null);
    }

    public GhServiceAsync(GhApi ghApi) {
        instance = this;
        this.gh = ghApi;
        this.orgs = new TreeSet<>((o, o1) -> o.getId() - o1.getId());
        this.identities = new HashMap<>();
    }

    /**
     * Request an Organization
     * @param login
     * @return Future Org
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public CompletableFuture<IGhOrg> getOrg(String login) throws ExecutionException, InterruptedException {
        return this.gh.getOrg(login).thenApplyAsync((GhOrgDto dto) -> {
            IGhOrg org = new GhOrg(dto);
            this.orgs.add(org);

            return org;
        });
    }

    /**
     * Request Contributors of a Repository
     * @param login
     * @param name
     * @param org
     * @return Future Stream
     */
    public CompletableFuture<Stream<IGhUser>> getRepoContributors(String login, String name, IGhOrg org) {
        CompletableFuture<List<GhUserDto>> future = this.gh.getRepoContributors(login, name, 1);
        return CompletableFuture.supplyAsync(() -> {
            Iterable<IGhUser> i = new ContributorsLazyStream(org, future);
            return StreamSupport.stream((i.spliterator()), false);
        });
    }

    public void addToIdentities(IGhUser user, IGhOrg orgObj) {
        Set<IGhOrg> set;

        if ((set = this.identities.get(user)) == null) {
            set = new TreeSet<>((o, o1) -> o.getId() - o1.getId());
            this.identities.put(user, set);
        }
        set.add(orgObj);
    }

    public IGhOrg containsOrg(GhOrgDto dto1) {
        for (IGhOrg org : this.orgs){
            if(org.getLogin().equals(dto1.login)){
                return org;
            }
        }
        return null;
    }

    public Stream<IGhOrg> getOrgs() {
        return this.orgs.stream();
    }

    @Override
    public void close() throws Exception {
        if (!gh.isClosed())
            gh.close();
    }

    public boolean isClosed() {
        return gh.isClosed();
    }

    public static GhServiceAsync getInstance() {
        if(instance == null){
            instance = new GhServiceAsync();
        }
        return instance;
    }
}
