package model.bugreports;

import java.util.ArrayList;
import java.util.List;

import controllers.exceptions.UnauthorizedAccessException;

public class PatchSection {
	
	private Patch acceptedPatch;
	private int satisfaction = -1;
	
	private List<Patch> patches;
	
	public PatchSection() {
		this.patches = new ArrayList<Patch>();
	}
	
	public void acceptPatch(String patch) throws UnauthorizedAccessException {
		if (!contains(patch)) throw new IllegalArgumentException("Patch not found.");
		
		this.acceptedPatch = getPatchByString(patch);
	}
	
	public void addPatch(String patch) {
		patches.add(new Patch(patch));
	}
	
	public void clear() {
		patches.clear();
		acceptedPatch = null;
		satisfaction = -1;
	}
	
	public void removePatch(String patch) {
		if (!contains(patch)) throw new IllegalArgumentException("No such Patch");
		
		patches.remove(getPatchByString(patch));
	}

	public String getAcceptedPatch() {
		if (acceptedPatch == null) throw new IllegalArgumentException("No Patch yet accepted");
		
		return acceptedPatch.getPatch();
	}
	
	public void updateSatisfaction(int satisfaction) {
		if (acceptedPatch == null) throw new IllegalArgumentException("No Patch yet accepted");
		if (satisfaction < 1 || satisfaction > 5) throw new IllegalArgumentException("Satisfaction out of bounds (1-5)");
		
		this.satisfaction = satisfaction;
	}
	
	public int getSatisfaction() {
		if (satisfaction == -1) throw new IllegalArgumentException("No satisfaction yet given.");
		
		return satisfaction;
	}
	
	public List<String> getPatchesAsList() {
		List<String> returnPatches = new ArrayList<String>();
		
		for (Patch patch : patches)
			returnPatches.add(patch.getPatch());
			
		return returnPatches;
	}
	
	public boolean contains(String containsPatch) {
		for (Patch patch : patches)
			if (patch.getPatch().equals(containsPatch))
				return true;
		return false;
	}

	private Patch getPatchByString(String string) {
		for (Patch patch : patches)
			if (patch.getPatch().equals(string))
				return patch;
		
		throw new IllegalArgumentException("No such patch");
	}
	
}
