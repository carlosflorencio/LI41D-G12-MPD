package isel.mpd.githubgw.model.streams;

import isel.mpd.githubgw.model.IGhOrg;
import isel.mpd.githubgw.model.IGhUser;
import isel.mpd.githubgw.model.async.GhOrg;
import isel.mpd.githubgw.model.async.GhServiceAsync;
import isel.mpd.githubgw.model.async.GhUser;
import isel.mpd.githubgw.webapi.dto.GhOrgDto;
import isel.mpd.githubgw.webapi.dto.GhUserDto;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class ContributorsLazyStream implements Iterable<IGhUser> {

    private static final int PER_PAGE = 30;

    private GhServiceAsync service;
    private List<GhUserDto> list;
    private IGhOrg orgObj;
    private int page;

    public ContributorsLazyStream(GhServiceAsync api, IGhOrg orgObj, CompletableFuture<List<GhUserDto>> l) {
        this.service = api;
        this.orgObj = orgObj;
        this.page = 1;
        try {
            list = l.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterator<IGhUser> iterator() {
        return new Iterator<IGhUser>() {
            int curr = 0;

            @Override
            public boolean hasNext() {
                try {
                    if (curr >= PER_PAGE * page) {
                        list.addAll(service.gh.getRepoContributors(orgObj.getLogin(), orgObj.getName(), ++page).get());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                return curr < list.size();
            }

            @Override
            public IGhUser next() {
                if (hasNext()) {
                    GhUserDto dto = list.get(curr);
                    CompletableFuture<Stream<IGhOrg>> future = service.gh.getUserOrgs(dto.login)
                            .thenApply((listOrgsDto) -> listOrgsDto.stream()
                                    .map((dtoAux) -> {
                                        IGhOrg organization = null;
                                        if ((organization = containsOrg(dtoAux)) != null) {
                                            return organization;
                                        } else {
                                            return new GhOrg(dtoAux, service);
                                        }
                                    }));

                    IGhUser user = new GhUser(dto, future);
                    Set<IGhOrg> set;

                    if ((set = service.identities.get(user)) == null) {
                        set = new TreeSet<>((o, o1) -> o.getId() - o1.getId());
                        service.identities.put(user, set);
                    }
                    set.add(orgObj);
                    curr++;
                    return user;
                }
                throw new NoSuchElementException();
            }

            private IGhOrg containsOrg(GhOrgDto dto1) {
                for (IGhOrg org : service.orgs) {
                    if (org.getLogin().equals(dto1.login)) {
                        return org;
                    }
                }
                return null;
            }
        };
    }
}
