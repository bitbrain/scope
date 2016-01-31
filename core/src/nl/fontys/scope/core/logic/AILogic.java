package nl.fontys.scope.core.logic;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.branch.Selector;
import com.badlogic.gdx.ai.btree.branch.Sequence;
import com.badlogic.gdx.math.Vector3;

import net.engio.mbassy.listener.Handler;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.World;
import nl.fontys.scope.core.logic.ai.AIState;
import nl.fontys.scope.core.logic.ai.AimEnemyTask;
import nl.fontys.scope.core.logic.ai.AimEnergyTask;
import nl.fontys.scope.core.logic.ai.AimSphereTask;
import nl.fontys.scope.core.logic.ai.ShootTask;
import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectType;
import nl.fontys.scope.util.GameObjectUtil;

public class AILogic implements Logic {

    private Player player;

    private Events events  = Events.getInstance();

    private BehaviorTree<AIState> tree;

    private Vector3 v = new Vector3();

    private World world;

    public AILogic(Player player, World world) {
        this.player = player;
        this.world = world;
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
        AIState state = tree.getObject();
        if (GameObjectType.SPHERE.equals(other.getType())) {
            state.sphere = other;
        } else if (GameObjectType.ENERGY.equals(other.getType())) {
            if (state.closestEnergy == null || GameObjectUtil.distanceTo(player.getShip(), other) < GameObjectUtil.distanceTo(player.getShip(), state.closestEnergy)) {
                state.closestEnergy = other;
            }
        } else if (GameObjectType.SHIP.equals(other.getType())) {
            if (state.closestEnemy == null || GameObjectUtil.distanceTo(player.getShip(), other) < GameObjectUtil.distanceTo(player.getShip(), state.closestEnemy)) {
                state.closestEnemy = other;
            }
        }
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



    @Handler
    public void onEvent(Events.GdxEvent event) {
        AIState state = tree.getObject();
        if (event.isTypeOf(EventType.ON_SHOT)) {
            GameObject source = world.getObjectById((String)event.getSecondaryParam(1));
            GameObject target = (GameObject) event.getSecondaryParam(0);
            if (source != null && target.equals(player.getShip())) {
                state.lastEnemyAttackedBy = source;
            }
        } else if (event.isTypeOf(EventType.OBJECT_REMOVED)) {
            GameObject object = (GameObject) event.getPrimaryParam();
            if (object.equals(state.closestEnemy)) {
                state.closestEnemy = null;
            } else if (object.equals(state.closestEnergy)) {
                state.closestEnergy = null;
            } else if (object.equals(state.lastEnemyAttackedBy)) {
                state.lastEnemyAttackedBy = null;
            }
        }
    }
}
