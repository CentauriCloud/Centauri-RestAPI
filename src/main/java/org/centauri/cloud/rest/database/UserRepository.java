package org.centauri.cloud.rest.database;

import org.centauri.cloud.cloud.database.Database;
import org.jooq.generated.rest.tables.pojos.User;
import org.jooq.generated.rest.tables.records.UserRecord;

import java.util.List;
import java.util.Optional;

import static org.jooq.generated.rest.tables.User.USER;

public class UserRepository {

	private Database database = Database.getInstance();

	public int createNewUser(User user) {
		return database.execResult(dslContext -> {
			UserRecord userRecord = dslContext.newRecord(USER, user);
			userRecord.store();
			return userRecord.getId();
		});
	}

	public String authenticate(String email, String password) {
		return database.execResult(context -> {
			UserRecord record = context.selectFrom(USER)
					.where(USER.EMAIL.eq(email))
					.and(USER.PASSWORD.eq(password))
					.fetchOne();
			record.setLastlogin(null);
			record.store();
			return record.getUsername();
		});
	}

	public Optional<User> getUser(int id) {
		return database.execResult(dslContext -> {
			Optional<UserRecord> optional = dslContext.fetchOptional(USER, USER.ID.eq(id));
			return optional.map(userRecord -> userRecord.into(User.class));
		});
	}

	public boolean delete(int id) {
		return database.execResult(dslContext -> dslContext.delete(USER)
				.where(USER.ID.eq(id))
				.execute()) > 0;
	}

	public boolean update(User user) {
		return database.execResult(dslContext -> {
			UserRecord record = dslContext.fetchOne(USER, USER.ID.eq(user.getId()));
			record.setUsername(user.getUsername());
			record.setPassword(user.getPassword());
			record.setEmail(user.getEmail());
			record.setActive(user.getActive());
			record.setGroupFk(user.getGroupFk());
			return record.store() > 0;
		});
	}

	public List<User> getUsers() {
		return database.execResult(dslContext -> dslContext.selectFrom(USER).fetch().into(User.class));
	}
}
