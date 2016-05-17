package tests.projecttests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.projects.ProjectTeam;
import model.projects.Role;
import model.users.Developer;

public class ProjectTeamTests {

    ProjectTeam  team1;
    Developer lead;
    Developer prog;
    Developer tester;

    @Before
    public void setUp() {
        team1 = new ProjectTeam();
        lead = new Developer("", "", "", "lead");
        prog = new Developer("", "", "", "prog");
        tester = new Developer("", "", "", "tester");
        team1.addMember(lead, Role.LEAD);
        team1.addMember(prog, Role.PROGRAMMER);
        team1.addMember(tester, Role.TESTER);
    }

    @Test
    public void addMemberTest() {
        ProjectTeam team = new ProjectTeam();
        team.addMember(lead, Role.LEAD);
        team.addMember(prog, Role.PROGRAMMER);
        team.addMember(tester, Role.TESTER);

        assertEquals(lead, team.getLeadDeveloper());
        assertEquals(prog, team.getProgrammers().get(0));
        assertEquals(tester, team.getTesters().get(0));

        //add already added member
        team.addMember(prog, Role.PROGRAMMER);
        team.addMember(tester, Role.TESTER);

        assertEquals(prog, team.getProgrammers().get(0));
        assertEquals(tester, team.getTesters().get(0));
        assertEquals(1, team.getProgrammers().size());
        assertEquals(1, team.getTesters().size());
    }

    @Test (expected = NullPointerException.class)
    public void addMemberNullTest() {
        team1.addMember(null, Role.LEAD);
    }

    @Test (expected = UnsupportedOperationException.class)
    public void addMemberSecondLeadTest() {
        team1.addMember(prog, Role.LEAD);
    }

    @Test
    public void getLeadDeveloperTest() {
        assertEquals(lead, team1.getLeadDeveloper());
        ProjectTeam team = new ProjectTeam();
        assertNull(team.getLeadDeveloper());
    }

    @Test
    public void setLeadDeveloperTest() {
        assertEquals(lead, team1.getLeadDeveloper());
        team1.setLeadDeveloper(prog);
        assertEquals(lead, team1.getProgrammers().get(team1.getProgrammers().size() - 1));
        assertEquals(prog, team1.getLeadDeveloper());
        //set lead to already lead
        team1.setLeadDeveloper(prog);
        assertEquals(lead, team1.getProgrammers().get(team1.getProgrammers().size() - 1));
        assertEquals(prog, team1.getLeadDeveloper());
    }
    
    @Test
    public void getRolesNotAssignedToTest() {
        List<Role> roles = null;
        
        roles = team1.getRolesNotAssignedTo(lead);
        assertEquals(2, roles.size());
        assertTrue(roles.contains(Role.PROGRAMMER));
        assertTrue(roles.contains(Role.TESTER));
        
        roles = team1.getRolesNotAssignedTo(prog);
        assertEquals(1, roles.size());
        assertTrue(roles.contains(Role.TESTER));
        
        roles = team1.getRolesNotAssignedTo(tester);
        assertEquals(1, roles.size());
        assertTrue(roles.contains(Role.PROGRAMMER));
    }
}