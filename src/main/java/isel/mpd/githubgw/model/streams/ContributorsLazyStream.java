package isel.mpd.githubgw.model.streams;

import isel.mpd.githubgw.model.IGhOrg;
import isel.mpd.githubgw.model.IGhUser;
import isel.mpd.githubgw.model.async.GhOrg;
import isel.mpd.githubgw.model.async.GhServiceAsync;
import isel.mpd.githubgw.model.async.GhUser;
import isel.mpd.githubgw.webapi.dto.GhUserDto;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class ContributorsLazyStream implements Iterable<IGhUser> {

    private static final int PER_PAGE = 30;

    private GhServiceAsync service;
    private List<GhUserDto> list;
    private IGhOrg organization;
    private int page;

    public ContributorsLazyStream(IGhOrg organization, CompletableFuture<List<GhUserDto>> l){
        this.service = GhServiceAsync.getInstance();
        this.organization = organization;
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
                        list.addAll(service.gh.getRepoContributors(organization.getLogin(), organization.getName(), ++page).get());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                return curr < list.size();
            }

            @Override
            public IGhUser next() {
                if(hasNext()) {
                    GhUserDto userDto = list.get(curr++);

                    //Convert from Org dto
                    CompletableFuture<Stream<IGhOrg>> futureOrgs = service.gh.getUserOrgs(userDto.login)
                            .thenApply((listOrgsDto) -> listOrgsDto.stream()
                                    .map((orgDto) -> {
                                        //Cached Organizations
                                        IGhOrg organization = null;
                                        if ((organization = service.containsOrg(orgDto)) != null) {
                                            return organization;
                                        } else {
                                            return new GhOrg(orgDto);
                                        }
                                    }));

                    IGhUser user = new GhUser(userDto, futureOrgs);
                    service.addToIdentities(user, organization);

                    return user;
                }
                throw new NoSuchElementException();
            }
        };
    }
}
