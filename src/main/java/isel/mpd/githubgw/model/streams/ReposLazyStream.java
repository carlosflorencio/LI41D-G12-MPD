package isel.mpd.githubgw.model.streams;

import isel.mpd.githubgw.model.IGhOrg;
import isel.mpd.githubgw.model.IGhRepo;
import isel.mpd.githubgw.model.IGhUser;
import isel.mpd.githubgw.model.async.GhRepo;
import isel.mpd.githubgw.model.async.GhServiceAsync;
import isel.mpd.githubgw.webapi.dto.GhRepoDto;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class ReposLazyStream implements Iterable<IGhRepo> {

    private static final int PER_PAGE = 30;

    private GhServiceAsync service;
    private List<GhRepoDto> list;
    private int orgId;
    private int page;
    private static Map<String, Future<Stream<IGhUser>>> cachedContributors; //maybe another way?

    static {
        cachedContributors = new HashMap<>();
    }

    public ReposLazyStream(int orgId, Future<List<GhRepoDto>> l) {
        this.service = GhServiceAsync.getInstance();
        this.orgId = orgId;
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
                    if (curr >= PER_PAGE * page) {
                        list.addAll(service.gh.getOrgRepos(orgId, ++page).get());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                return curr < list.size();
            }

            @Override
            public IGhRepo next() {
                if (hasNext()) {
                    IGhOrg org = service.getOrgs()
                            .filter(k -> k.getId() == orgId)
                            .findFirst()
                            .get();

                    //Cache Future Contributors
                    String repoName = list.get(curr).name;
                    Future<Stream<IGhUser>> fUsers = cachedContributors.get(repoName);
                    if (fUsers == null) {
                        fUsers = service.getRepoContributors(org.getLogin(), repoName, org);
                        cachedContributors.put(repoName, fUsers);
                    }

                    return new GhRepo(list.get(curr++), org, fUsers);
                }
                throw new NoSuchElementException();
            }
        };
    }

    /**
     * Clear static cache
     */
    public static void clearCache() {
        cachedContributors.clear();
    }
}
