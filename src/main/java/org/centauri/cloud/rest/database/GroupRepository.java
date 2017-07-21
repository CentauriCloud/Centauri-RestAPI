package org.centauri.cloud.rest.database;

import org.centauri.cloud.cloud.database.Database;
import org.jooq.generated.rest.tables.pojos.Group;
import org.jooq.generated.rest.tables.records.GroupRecord;

import java.util.List;
import java.util.Optional;

import static org.jooq.generated.rest.tables.Group.GROUP;

public class GroupRepository {

	private Database database = Database.getInstance();

	public List<Group> getGroups() {
		return database.execResult(dslContext -> dslContext.selectFrom(GROUP).fetch().into(Group.class));
	}

	public Optional<Group> getGroup(int id) {
		return database.execResult(dslContext -> {
			Optional<GroupRecord> optional = dslContext.fetchOptional(GROUP, GROUP.ID.eq(id));
			return optional.map(userRecord -> userRecord.into(Group.class));
		});
	}

	public int createNewGroup(Group group) {
		return database.execResult(dslContext -> {
			GroupRecord record = dslContext.newRecord(GROUP, group);
			record.store();
			return record.getId();
		});
	}


}
