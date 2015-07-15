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
import isel.mpd.githubgw.model.streams.ReposLazyStream;
import isel.mpd.githubgw.webapi.dto.GhOrgDto;
import isel.mpd.githubgw.webapi.dto.GhRepoDto;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by Miguel Gamboa on 05-06-2015.
 */
public class GhOrg implements IGhOrg {

    private final int id;

    private final String login;

    private final String name;

    private final String location;

    private final GhServiceAsync service;

    private Future<List<GhRepoDto>> futureList;

    public GhOrg(
            int id,
            String login,
            String name,
            String location,
            GhServiceAsync service) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.location = location;
        this.service = service;
        this.futureList = this.service.gh.getOrgRepos(this.id);
    }

    public GhOrg(
            GhOrgDto dto,
            GhServiceAsync service) {
        this(dto.id, dto.login, dto.name, dto.location, service);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public Stream<IGhRepo> getRepos() {
        ReposLazyStream i = new ReposLazyStream(service, id, futureList);
        return StreamSupport.stream((i.spliterator()), false);
    }
}
