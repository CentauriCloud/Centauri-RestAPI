package org.centauri.cloud.rest.resource;

import io.swagger.annotations.Api;
import org.centauri.cloud.cloud.database.Database;
import org.centauri.cloud.cloud.database.PSBuilder;
import org.centauri.cloud.rest.annotations.Nothing;
import org.centauri.cloud.rest.annotations.Returns;
import org.centauri.cloud.rest.annotations.Takes;
import org.centauri.cloud.rest.filter.LoginFilter;
import org.centauri.cloud.rest.jwt.JWTUtil;
import org.centauri.cloud.rest.to.auth.AuthTO;
import org.centauri.cloud.rest.to.auth.JwtTO;
import org.centauri.cloud.rest.to.group.GroupInformationTO;
import org.centauri.cloud.rest.to.group.GroupTO;
import org.centauri.cloud.rest.to.user.UserInformationTO;
import org.centauri.cloud.rest.to.user.UserTO;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static org.centauri.cloud.rest.util.ResponseFactory.fail;
import static org.centauri.cloud.rest.util.ResponseFactory.ok;

@Api(value = "/users", description = "Mostly actions with users and login")
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	private Database database = Database.getInstance();

	@POST
	@Path("/login")
	@Takes(AuthTO.class)
	@Returns(JwtTO.class)
	public Response authentication(final AuthTO authTO) {
		boolean authenticated = database.execResult(connection -> {
			PreparedStatement ps = PSBuilder.builder()
					.connection(connection)
					.query("SELECT * FROM user WHERE username=? AND password=?")
					.variable(authTO.getUsername())
					.variable(authTO.getPassword())
					.build();
			ResultSet rs = ps.executeQuery();
			return rs.next();
		});
		if (authenticated) {
			return ok(JWTUtil.generateToken(LoginFilter.UserType.USER, ""));
		}
		return fail("Wrong credentials");
	}

	@POST
	@Path("/new")
	@RolesAllowed("ADMIN")
	@Takes(UserInformationTO.class)
	@Returns(Nothing.class)
	public Response createNewUser(final UserInformationTO informationTO) {
		database.execVoid(connection -> {
			PreparedStatement ps = PSBuilder.builder()
					.connection(connection)
					.query("INSERT INTO user (username, password, email, group) VALUES (?, ?, ?, ?)")
					.variable(informationTO.getUserGroup())
					.variable(informationTO.getPassword())
					.variable(informationTO.getEmail())
					.variable(informationTO.getUserGroup())
					.build();
			ps.executeUpdate();

		}, false);
		return ok();
	}

	@GET
	@Path("/{id}")
	@Takes(Nothing.class)
	@Returns(UserInformationTO.class)
	public Response getUserInformation(@PathParam("id") int userId) {
		UserInformationTO informationTO = database.execResult(connection -> {
			PreparedStatement ps = PSBuilder.builder()
					.connection(connection)
					.query("SELECT * FROM user WHERE id=?")
					.variable(userId)
					.build();
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return new UserInformationTO(
						rs.getString("username"),
						rs.getString("email"),
						rs.getString("password"),
						null,
						rs.getString("group"),
						rs.getInt("lastLogin"),
						rs.getBoolean("active"));
			return null;
		});
		if (informationTO == null)
			return fail("Could not find user with given id");
		return ok(informationTO);
	}

	@DELETE
	@Path("/{id}")
	@Takes(Nothing.class)
	@Returns(Nothing.class)
	public Response deleteUser(@PathParam("id") int userId) {
		//TODO need wifi to pull centauri master, get from db
		return Response.status(200).build();
	}

	@POST
	@PathParam("/{id}")
	@Takes(UserInformationTO.class)
	@Returns(Nothing.class)
	public Response updateUser(@PathParam("id") int userId, UserInformationTO informationTO) {
		//TODO need wifi to pull centauri master, get from db
		return Response.status(200).build();

	}

	@GET
	@Path("/")
	@Takes(Nothing.class)
	@Returns(UserTO.class)
	public Response getAllUsers() {
		//TODO need wifi to pull centauri master, get from db
		return Response.status(200).entity(new ArrayList<UserTO>()).build();
	}

	@GET
	@Path("/groups")
	@Takes(Nothing.class)
	@Returns(GroupTO.class)
	public Response getAllUserGroups() {
		//TODO need wifi to pull centauri master, get from db
		return Response.status(200).entity(new ArrayList<GroupTO>()).build();
	}

	@GET
	@Path("/groups/{id}")
	@Takes(Nothing.class)
	@Returns(GroupInformationTO.class)
	public Response getUserGroupDetails(@PathParam("id") int groupId) {
		//TODO need wifi to pull centauri master, get from db
		return Response.status(200).entity(new GroupInformationTO()).build();
	}

	@POST
	@Path("/groups")
	@Takes(GroupInformationTO.class)
	@Returns(Nothing.class)
	public Response createNewUserGroup(GroupInformationTO groupInformationTO) {
		//TODO need wifi to pull centauri master, get from db
		return Response.status(200).build();
	}

}
