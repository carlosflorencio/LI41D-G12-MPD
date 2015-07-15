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

    public ContributorsLazyStream(GhServiceAsync api, IGhOrg orgObj, CompletableFuture<List<GhUserDto>> l){
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
                    if(curr >= PER_PAGE * page){
                        list.addAll(service.gh.getRepoContributors(orgObj.getLogin(), orgObj.getName(), ++page).get());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace(); // Ooops, try again?
                }

                return curr < list.size();
            }

            @Override
            public IGhUser next() {
                if(hasNext()) {
                    GhUserDto dto = list.get(curr++);

                    //Convert from Org dto
                    CompletableFuture<Stream<IGhOrg>> futureOrgs = service.gh.getUserOrgs(dto.login)
                            .thenApply((listOrgsDto) -> listOrgsDto.stream()
                                    .map((dtoAux) -> {
                                        //Cached Organizations
                                        IGhOrg organization = null;
                                        if ((organization = service.containsOrg(dtoAux)) != null) {
                                            return organization;
                                        } else {
                                            return new GhOrg(dtoAux, service);
                                        }
                                    }));

                    IGhUser user = new GhUser(dto, futureOrgs);
                    service.addToIdentities(user, orgObj);

                    return user;
                }
                throw new NoSuchElementException();
            }
        };
    }
}
