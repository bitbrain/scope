package nl.fontys.scope.controls;

enum MoveableAction {
    RISE,
    BOOST {
        @Override
        public void act(Moveable moveable) {
            moveable.boost();
        }
    },
    ROTATE,
    SHOOT {
        @Override
        public void act(Moveable moveable) {
            moveable.shoot();
        }
    };

    public void act(Moveable moveable) {
        // noOp
    }
}
