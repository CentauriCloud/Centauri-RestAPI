package org.centauri.cloud.rest.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.centauri.cloud.cloud.database.Database;
import org.centauri.cloud.cloud.database.PSBuilder;
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
	@ApiOperation(value = "this endpoint must be called before any others to authenticate", response = JwtTO.class)
	public Response authentication(@ApiParam(value = "Authentication information (username, password)", required = true) final AuthTO authTO) {
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
	@ApiOperation(value = "creates a new user. Needs admin")
	public Response createNewUser(@ApiParam(value = "the given data for the new user", required = true) final UserInformationTO informationTO) {
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
	@ApiOperation(value = "gets some information about a single user", response = UserInformationTO.class)
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
	@ApiOperation(value = "deletes a user with the given id")
	public Response deleteUser(@PathParam("id") int userId) {
		//TODO need wifi to pull centauri master, get from db
		return Response.status(200).build();
	}

	@POST
	@PathParam("/{id}")
	@ApiOperation(value = "updates an existing user")
	public Response updateUser(@PathParam("id") int userId, @ApiParam(value = "the new information from the user", required = true) UserInformationTO informationTO) {
		//TODO need wifi to pull centauri master, get from db
		return Response.status(200).build();

	}

	@GET
	@Path("/")
	@ApiOperation(value = "gets a list of all existing users", response = UserTO.class, responseContainer = "List")
	public Response getAllUsers() {
		//TODO need wifi to pull centauri master, get from db
		return Response.status(200).entity(new ArrayList<UserTO>()).build();
	}

	@GET
	@Path("/groups")
	@ApiOperation(value = "gets a list of all existing groups", response = GroupTO.class, responseContainer = "List")
	public Response getAllUserGroups() {
		//TODO need wifi to pull centauri master, get from db
		return Response.status(200).entity(new ArrayList<GroupTO>()).build();
	}

	@GET
	@Path("/groups/{id}")
	@ApiOperation(value = "gets some information about a single group", response = GroupInformationTO.class)
	public Response getUserGroupDetails(@PathParam("id") int groupId) {
		//TODO need wifi to pull centauri master, get from db
		return Response.status(200).entity(new GroupInformationTO()).build();
	}

	@POST
	@Path("/groups")
	@ApiOperation(value = "creates a new group")
	public Response createNewUserGroup(@ApiParam(value = "the information for the new group", required = true) GroupInformationTO groupInformationTO) {
		//TODO need wifi to pull centauri master, get from db
		return Response.status(200).build();
	}

}
