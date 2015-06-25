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

public class ContributorsLazyStream<T> implements Iterable<IGhRepo> {

    private GhServiceAsync service;
    private int id;
    private List<GhRepoDto> f;

    public ContributorsLazyStream(GhServiceAsync api, int id, CompletableFuture<List<GhRepoDto>> f) {
        this.service = api;
        this.id = id;
        try {
            this.f = f.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterator<IGhRepo> iterator() {
        return new Iterator<IGhRepo>() {
            List<IGhRepo> list = new LinkedList<>();
            int perPage = 30;
            int page = 1;
            int curr = 0;

            @Override
            public boolean hasNext() {
                if (list.isEmpty()) {
                    return firstTime();
                }

                System.out.println(curr + " x= " + perPage * page);
                if (curr >= perPage * page) {
                    System.out.println("limit");
                    page++;
                    return fillList();
                }

                return curr < list.size();
            }

            @Override
            public IGhRepo next() {
                return list.get(curr++);
            }

            public boolean firstTime() {
                int before = list.size();
                f.forEach(o -> {
                    IGhOrg org = service.orgs.stream()
                            .filter((k) -> k.getId() == id)
                            .findFirst()
                            .orElse(null);
                    CompletableFuture<Stream<IGhUser>> future = service.getRepoContributors(org.getLogin(),
                                                                                            o.name,
                                                                                            org);
                    IGhRepo repo = new GhRepo(o, org, future);
                    list.add(repo);
                });

                return list.size() != before;
            }

            public boolean fillList() {
                int before = list.size();
                try {
                    service.gh.getOrgRepos(id, page).get().forEach(o -> {
                        IGhOrg org = service.orgs.stream()
                                .filter((k) -> k.getId() == id)
                                .findFirst()
                                .orElse(null);
                        Future<Stream<IGhUser>> future = service.getRepoContributors(org.getLogin(), o.name, org);
                        IGhRepo repo = new GhRepo(o, org, future);
                        list.add(repo);

                    });
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                return list.size() != before;
            }


        };
    }
}
