package isel.mpd.githubgw.model.streams;

import isel.mpd.githubgw.model.IGhOrg;
import isel.mpd.githubgw.model.IGhRepo;
import isel.mpd.githubgw.model.IGhUser;
import isel.mpd.githubgw.model.async.GhRepo;
import isel.mpd.githubgw.model.async.GhServiceAsync;
import isel.mpd.githubgw.webapi.dto.GhRepoDto;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class ReposLazyStream implements Iterable<IGhRepo> {

    private static final int PER_PAGE = 30;

    private GhServiceAsync service;
    private List<GhRepoDto> list;
    private int id;
    private int page;
    private static Map<String, Future<Stream<IGhUser>>> cachedContributors;

    static {
        cachedContributors = new HashMap<>();
    }

    public ReposLazyStream(GhServiceAsync service, int id,Future<List<GhRepoDto>> l ){
        this.service = service;
        this.id = id;
        this.page = 1;
        try {
            list = l.get();
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
                        list.addAll(service.gh.getOrgRepos(id, ++page).get());
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

                    String repoName = list.get(curr).name;
                    Future<Stream<IGhUser>> fUsers = cachedContributors.get(repoName);
                    if(fUsers == null){
                        fUsers = service.getRepoContributors(org.getLogin(),repoName,org);
                        cachedContributors.put(repoName, fUsers);
                    }

                    return new GhRepo(list.get(curr++), org, fUsers);
                }
                throw new NoSuchElementException();
            }
        };
    }
}
