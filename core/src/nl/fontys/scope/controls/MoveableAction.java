package nl.fontys.scope.controls;

enum MoveableAction {
    RISE,
    BOOST {
        @Override
        public void act(Moveable moveable, Object ... args) {
            if (args.length == 1 && args[0] instanceof Float) {
                moveable.boost((Float)args[0]);
            } else {
                moveable.boost(1f);
            }
        }
    },
    ROTATE,
    SHOOT {
        @Override
        public void act(Moveable moveable, Object ... args) {
            moveable.shoot();
        }
    };

    public void act(Moveable moveable, Object ... args) {
        // noOp
    }
}
