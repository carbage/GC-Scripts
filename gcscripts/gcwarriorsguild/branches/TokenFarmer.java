package gcscripts.gcwarriorsguild.branches;

import org.powerbot.core.script.job.state.Branch;
import org.powerbot.core.script.job.state.Node;

public class TokenFarmer extends Branch {

	public TokenFarmer(Node[] nodes) {
		super(nodes);
	}

	@Override
	public boolean branch() {
		return true;
	}

}
