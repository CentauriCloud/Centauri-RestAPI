package org.centauri.cloud.rest.resource;

import com.google.common.collect.Lists;
import io.swagger.annotations.*;
import org.centauri.cloud.rest.database.GroupRepository;
import org.centauri.cloud.rest.database.UserRepository;
import org.centauri.cloud.rest.jwt.JWTUtil;
import org.centauri.cloud.rest.to.auth.AuthTO;
import org.centauri.cloud.rest.to.auth.JwtTO;
import org.centauri.cloud.rest.to.group.GroupInformationTO;
import org.centauri.cloud.rest.to.group.GroupTO;
import org.centauri.cloud.rest.to.user.UserInformationTO;
import org.centauri.cloud.rest.to.user.UserTO;
import org.jooq.generated.rest.tables.pojos.Group;
import org.jooq.generated.rest.tables.pojos.User;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.centauri.cloud.rest.util.ResponseFactory.fail;
import static org.centauri.cloud.rest.util.ResponseFactory.ok;

@Api(value = "/users", description = "Mostly actions with users and login")
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SwaggerDefinition(
		securityDefinition = @SecurityDefinition(
				apiKeyAuthDefinitions = {
						@ApiKeyAuthDefinition(key = "Bearer", name = "Authorization", in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER)
				}
		)
)
public class UserResource {

	private UserRepository userRepository = new UserRepository();
	private GroupRepository groupRepository = new GroupRepository();

	@POST
	@Path("/login")
	@ApiOperation(value = "this endpoint must be called before any others to authenticate", response = JwtTO.class)
	public Response authentication(@ApiParam(value = "Authentication information (username, password)", required = true) @NotNull @Valid final AuthTO authTO) {
		String username = userRepository.authenticate(authTO.getEmail(), authTO.getPassword());
		if (username != null)
			return ok(new JwtTO(JWTUtil.generateToken(Lists.newArrayList("ADMIN"), username)));
		return fail("Wrong credentials");
	}

	@POST
	@Path("/new")
	@ApiOperation(value = "creates a new user. Needs admin", authorizations = @Authorization("Bearer"))
	@RolesAllowed({"ADMIN", "users.new"})
	public Response createNewUser(@ApiParam(value = "the given data for the new user", required = true) @NotNull @Valid final UserInformationTO informationTO) {
		User user = new User(
				null,
				informationTO.getUsername(),
				informationTO.getPassword(),
				informationTO.getEmail(),
				informationTO.getUserGroupId(),
				null,
				informationTO.isActive()
		);
		int id = userRepository.createNewUser(user);
		informationTO.setId(id);
		return ok(informationTO);
	}

	@GET
	@Path("/{id}")
	@ApiOperation(value = "gets some information about a single user", response = UserInformationTO.class, authorizations = @Authorization("Bearer"))
	@RolesAllowed({"ADMIN", "users.info"})
	public Response getUserInformation(@PathParam("id") int userId) {
		Optional<User> optional = userRepository.getUser(userId);
		if (!optional.isPresent())
			return fail("Could not find user with given id");
		User user = optional.get();
		UserInformationTO informationTO = new UserInformationTO(
				user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
				null,
				user.getGroupFk(),
				user.getLastlogin().getTime(),
				user.getActive()
		);
		return ok(informationTO);
	}

	@DELETE
	@Path("/{id}")
	@ApiOperation(value = "deletes a user with the given id")
	@RolesAllowed({"ADMIN", "users.delete"})
	public Response deleteUser(@PathParam("id") int userId) {
		boolean didDelete = userRepository.delete(userId);
		if (didDelete)
			return ok();
		return fail("Could not find given user");
	}

	@POST
	@Path("/{id}")
	@ApiOperation(value = "updates an existing user", authorizations = @Authorization("Bearer"))
	@RolesAllowed({"ADMIN", "users.update"})
	public Response updateUser(@PathParam("id") int userId, @ApiParam(value = "the new information from the user", required = true) @NotNull @Valid final UserInformationTO informationTO) {
		User user = new User(
				userId,
				informationTO.getUsername(),
				informationTO.getPassword(),
				informationTO.getEmail(),
				informationTO.getUserGroupId(),
				null,
				informationTO.isActive()
		);
		boolean updated = userRepository.update(user);
		if (updated)
			return ok();
		return fail("Could not find given user");

	}

	@GET
	@Path("/")
	@ApiOperation(value = "gets a list of all existing users", response = UserTO.class, responseContainer = "List", authorizations = @Authorization("Bearer"))
	@RolesAllowed({"ADMIN", "users.all"})
	public Response getAllUsers() {
		List<User> users = userRepository.getUsers();
		List<UserTO> userTOS = users.stream()
				.map(user -> new UserTO(user.getUsername(), user.getPassword(), user.getActive()))
				.collect(Collectors.toList());
		return ok(userTOS);
	}

	@GET
	@Path("/groups")
	@ApiOperation(value = "gets a list of all existing groups", response = GroupTO.class, responseContainer = "List")
	public Response getAllUserGroups() {
		List<Group> groups = groupRepository.getGroups();
		List<GroupTO> groupTOS = groups.stream()
				.map(group -> new GroupTO(group.getName()))
				.collect(Collectors.toList());
		return ok(groupTOS);
	}

	@GET
	@Path("/groups/{id}")
	@ApiOperation(value = "gets some information about a single group", response = GroupInformationTO.class)
	public Response getUserGroupDetails(@PathParam("id") int groupId) {
		Optional<Group> optional = groupRepository.getGroup(groupId);
		if (optional.isPresent()) {
			Group group = optional.get();
			GroupInformationTO informationTO = new GroupInformationTO(group.getId(), group.getName(), group.getDescription(), group.getActive(), null);
			return ok(informationTO);
		}
		return fail("no group with given id found");
	}

	@POST
	@Path("/groups")
	@ApiOperation(value = "creates a new group")
	public Response createNewUserGroup(@ApiParam(value = "the information for the new group", required = true) @NotNull @Valid GroupInformationTO groupInformationTO) {
		Group group = new Group(null, groupInformationTO.getName(), groupInformationTO.getDescription(), groupInformationTO.isActive());
		int id = groupRepository.createNewGroup(group);
		groupInformationTO.setId(id);
		return ok(groupInformationTO);
	}

}
