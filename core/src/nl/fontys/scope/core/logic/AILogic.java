package nl.fontys.scope.core.logic;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.branch.Selector;
import com.badlogic.gdx.ai.btree.branch.Sequence;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.logic.ai.AIState;
import nl.fontys.scope.core.logic.ai.AimEnemyTask;
import nl.fontys.scope.core.logic.ai.AimEnergyTask;
import nl.fontys.scope.core.logic.ai.AimSphereTask;
import nl.fontys.scope.core.logic.ai.ShootTask;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.object.GameObject;

public class AILogic implements Logic {

    private Player player;

    private Events events  = Events.getInstance();

    private BehaviorTree<AIState> tree;

    public AILogic(Player player) {
        this.player = player;
        events.register(this);
        tree = buildBehaviorTree();
        tree.getObject().player = player;
    }

    @Override
    public void update(GameObject object, float delta) {
        tree.getObject().delta = delta;
        tree.step();
    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {
        tree.getObject().otherObject = other;
    }

    private BehaviorTree<AIState> buildBehaviorTree() {
        // Layer 1
        Selector selector = new Selector();
        // Layer 2 (left)
        Sequence sLeft = new Sequence();
        selector.addChild(sLeft);
        // Layer 2 (right)
        Selector sRight = new Selector();
        selector.addChild(sRight);
        // Layer 3 (left)
        sLeft.addChild(new ShootTask());
        sLeft.addChild(new AimEnemyTask());
        // Layer 3 (right)
        sRight.addChild(new AimEnergyTask());
        sRight.addChild(new AimSphereTask());
        return new BehaviorTree<AIState>(selector, new AIState());
    }
}
