package gcscripts.gcpowerminer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Job;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.Random;

@Manifest(authors = { "ArcaneSanity" }, name = "Script Skeleton" )
public class ScriptSkeleton extends ActiveScript {

        private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
        private Tree jobContainer = null;

        public synchronized final void provide(final Node... jobs) {
                for (final Node job : jobs) {
                        if(!jobsCollection.contains(job)) {
                                jobsCollection.add(job);
                        }
                }
                jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
        }

        public synchronized final void revoke(final Node... jobs) {
                for (final Node job : jobs) {
                        if(jobsCollection.contains(job)) {
                                jobsCollection.remove(job);
                        }
                }
                jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
        }

        public final void submit(final Job... jobs) {
                for (final Job job : jobs) {
                        getContainer().submit(job);
                }
        }

        @Override
        public void onstart() {
                provide(new Node1());
        }

        @Override
        public int loop() {
                if (jobContainer != null) {
                        final Node job = jobContainer.state();
                        if (job != null) {
                                jobContainer.set(job);
                                getContainer().submit(job);
                                job.join();
                        }
                }
                return Random.nextInt(10, 50);
        }

        public class Node1 extends Node {
                @Override
                public boolean activate() {
                        return false;
                }

                @Override
                public void execute() {

                }
        }

}
