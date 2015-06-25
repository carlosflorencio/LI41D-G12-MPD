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
package isel.mpd.githubgw.webapi;

import com.ning.http.client.Response;
import isel.mpd.githubgw.webapi.dto.GhOrgDto;
import isel.mpd.githubgw.webapi.dto.GhRepoDto;
import isel.mpd.githubgw.webapi.dto.GhUserDto;
import isel.mpd.jsonzai.JsonParser;
import isel.mpd.util.HttpGwAsync;
import isel.mpd.util.Pair;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Miguel Gamboa on 05-06-2015.
 */
public class GhApi implements AutoCloseable{

    final static String GH_URI = "https://api.github.com";
    final static String GH_VERSION = "application/vnd.github.v3+json";
    final static String GH_TOKEN = "token 935fb49c6793577c1d0bfa92999c44ec8206db30";
    final static Pair<String, String>[] GH_HEADERS;

    static{
        GH_HEADERS = new Pair[2];
        GH_HEADERS[0] = new Pair<String, String>("Accept", GH_VERSION);
        GH_HEADERS[1] = new Pair<String, String>("Authorization", GH_TOKEN);
    }

    private final HttpGwAsync httpGw;
    private final JsonParser reader;

    public GhApi() {
        this(new HttpGwAsync(), new JsonParser());
    }

    public GhApi(HttpGwAsync httpGw) {
        this(httpGw, new JsonParser());
    }

    public GhApi(HttpGwAsync httpGw, JsonParser reader) {
        this.httpGw = httpGw;
        this.reader = reader;
    }

    public CompletableFuture<GhUserDto> getUserInfo(String login){
        String uri = GH_URI + "/users/" + login;
        return httpGw.getDataAsync(uri, GH_HEADERS).thenApply(r -> jsonToObject(r, GhUserDto.class));
    }

    public CompletableFuture<GhOrgDto> getOrg(String name){
        String uri = GH_URI + "/orgs/" + name;
        return httpGw.getDataAsync(uri, GH_HEADERS).thenApply(r -> jsonToObject(r, GhOrgDto.class));
    }

    public CompletableFuture<List<GhRepoDto>> getOrgRepos(int id){
        return getOrgRepos(id, 1);
    }

    public CompletableFuture<List<GhRepoDto>> getOrgRepos(int id, int page){
        String uri = GH_URI + "/organizations/" + id + "/repos?page=" + page;
        return httpGw.getDataAsync(uri, GH_HEADERS).thenApply(r -> jsonToList(r, GhRepoDto.class));
    }

    public CompletableFuture<GhRepoDto> getRepo(String owner, String name){
        String uri = GH_URI + "/repos/" + owner + "/" + name;
        return httpGw.getDataAsync(uri, GH_HEADERS).thenApply(r -> jsonToObject(r, GhRepoDto.class));
    }

    public CompletableFuture<List<GhUserDto>> getRepoContributors(String login, String repo){
        return getRepoContributors(login, repo, 1);
    }

    public CompletableFuture<List<GhUserDto>> getRepoContributors(String login, String repo, int page){
        String uri = GH_URI + "/repos/" + login + "/" + repo + "/contributors?page=" + page;
        return httpGw.getDataAsync(uri, GH_HEADERS).thenApply(r -> jsonToList(r, GhUserDto.class));
    }

    public CompletableFuture<List<GhOrgDto>> getUserOrgs(String login){
        String uri = GH_URI + "/users/" + login + "/orgs";
        return httpGw.getDataAsync(uri, GH_HEADERS).thenApply(r -> jsonToList(r, GhOrgDto.class));
    }

    @Override
    public void close() throws Exception {
        if(!httpGw.isClosed())
            httpGw.close();
    }

    public boolean isClosed() {
        return httpGw.isClosed();
    }

    private <T> T jsonToObject(Response resp, Class<T> destKlass){
        try {
            return (T) reader.toObject(resp.getResponseBody(), destKlass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> List<T> jsonToList(Response resp, Class<T> elementType){
        try {
            return reader.toList(resp.getResponseBody(), elementType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
