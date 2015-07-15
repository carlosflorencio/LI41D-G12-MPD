package isel.mpd.githubgw.model.streams;

import isel.mpd.githubgw.model.IGhOrg;
import isel.mpd.githubgw.model.IGhRepo;
import isel.mpd.githubgw.model.IGhUser;
import isel.mpd.githubgw.model.async.GhRepo;
import isel.mpd.githubgw.model.async.GhServiceAsync;
import isel.mpd.githubgw.webapi.dto.GhRepoDto;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class ReposLazyStream implements Iterable<IGhRepo> {

    private static final int PER_PAGE = 30;

    private GhServiceAsync service;
    private List<GhRepoDto> list;
    private int id;
    private int page;

    public ReposLazyStream(GhServiceAsync api, int id){
        this.service = api;
        this.id = id;
        this.page = 1;
        try {
            list = service.gh.getOrgRepos(id, page).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterator<IGhRepo> iterator() {
        return new Iterator<IGhRepo>() {
            int curr = 0;

            @Override
            public boolean hasNext() {
                System.out.println(curr + " x= " + PER_PAGE * page);
                try {
                    if(curr >= PER_PAGE * page){
                        page++;
                        list.addAll(service.gh.getOrgRepos(id, page).get());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                return curr < list.size();
            }

            @Override
            public IGhRepo next() {
                if(hasNext()) {
                    IGhOrg org = service.orgs.stream()
                            .filter(k -> k.getId() == id)
                            .findFirst()
                            .get();
                    Future<Stream<IGhUser>> future = service.getRepoContributors(org.getLogin(), list.get(curr).name, org);
                    return new GhRepo(list.get(curr++), org, future);
                }
                throw new NoSuchElementException();
            }
        };
    }
}
