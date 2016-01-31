package nl.fontys.scope.core.logic.ai;

import nl.fontys.scope.core.Player;
import nl.fontys.scope.object.GameObject;

public class AIState {

    public GameObject closestEnergy;
    public GameObject sphere;
    public GameObject closestEnemy;
    public GameObject lastEnemyAttackedBy;
    public Player player;
    public float delta;
}
