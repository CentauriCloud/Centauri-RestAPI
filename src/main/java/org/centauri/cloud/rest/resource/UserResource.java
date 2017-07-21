package org.centauri.cloud.rest.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.centauri.cloud.rest.database.UserRepository;
import org.centauri.cloud.rest.filter.LoginFilter;
import org.centauri.cloud.rest.jwt.JWTUtil;
import org.centauri.cloud.rest.to.auth.AuthTO;
import org.centauri.cloud.rest.to.auth.JwtTO;
import org.centauri.cloud.rest.to.group.GroupInformationTO;
import org.centauri.cloud.rest.to.group.GroupTO;
import org.centauri.cloud.rest.to.user.UserInformationTO;
import org.centauri.cloud.rest.to.user.UserTO;
import org.jooq.generated.rest.tables.pojos.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.centauri.cloud.rest.util.ResponseFactory.fail;
import static org.centauri.cloud.rest.util.ResponseFactory.ok;

@Api(value = "/users", description = "Mostly actions with users and login")
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	private UserRepository repository = new UserRepository();

	@POST
	@Path("/login")
	@ApiOperation(value = "this endpoint must be called before any others to authenticate", response = JwtTO.class)
	public Response authentication(@ApiParam(value = "Authentication information (username, password)", required = true) final AuthTO authTO) {
		String username = repository.authenticate(authTO.getEmail(), authTO.getPassword());
		if (username != null) {
			return ok(new JwtTO(JWTUtil.generateToken(LoginFilter.UserType.USER, "")));
		}
		return fail("Wrong credentials");
	}

	@POST
	@Path("/new")
	@RolesAllowed("ADMIN")
	@ApiOperation(value = "creates a new user. Needs admin")
	public Response createNewUser(@ApiParam(value = "the given data for the new user", required = true) final UserInformationTO informationTO) {
		User user = new User(null, informationTO.getUsername(), informationTO.getPassword(), informationTO.getEmail(), informationTO.getUserGroup(), new Timestamp(informationTO.getLastLogin()), informationTO.isActive());
		repository.createNewUser(user);
		return ok();
	}

	@GET
	@Path("/{id}")
	@ApiOperation(value = "gets some information about a single user", response = UserInformationTO.class)
	public Response getUserInformation(@PathParam("id") int userId) {
		Optional<User> optional = repository.getUser(userId);
		if (!optional.isPresent())
			return fail("Could not find user with given id");
		User user = optional.get();
		UserInformationTO informationTO = new UserInformationTO(
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
				null,
				user.getUserGroup(),
				user.getLastlogin().getTime(),
				user.getActive()
		);
		return ok(informationTO);
	}

	@DELETE
	@Path("/{id}")
	@ApiOperation(value = "deletes a user with the given id")
	public Response deleteUser(@PathParam("id") int userId) {
		boolean didDelete = repository.delete(userId);
		if (didDelete)
			return ok();
		return fail("Could not find given user");
	}

	@POST
	@PathParam("/{id}")
	@ApiOperation(value = "updates an existing user")
	public Response updateUser(@PathParam("id") int userId, @ApiParam(value = "the new information from the user", required = true) UserInformationTO informationTO) {
		User user = new User(
				userId,
				informationTO.getUsername(),
				informationTO.getPassword(),
				informationTO.getEmail(),
				informationTO.getUserGroup(),
				new Timestamp(informationTO.getLastLogin()),
				informationTO.isActive()
		);
		boolean updated = repository.update(user);
		if (updated)
			return ok();
		return fail("Could not find given user");

	}

	@GET
	@Path("/")
	@ApiOperation(value = "gets a list of all existing users", response = UserTO.class, responseContainer = "List")
	public Response getAllUsers() {
		List<User> users = repository.getUsers();
		List<UserTO> userTOS = users
				.stream()
				.map(user -> new UserTO(user.getUsername(), user.getPassword(), user.getActive()))
				.collect(Collectors.toList());
		return ok(userTOS);
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
