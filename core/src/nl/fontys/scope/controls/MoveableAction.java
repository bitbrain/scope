package nl.fontys.scope.controls;

enum MoveableAction {
    RISE {
        @Override
        public void act(Moveable moveable, Object ... args) {
            if (args.length == 1 && args[0] instanceof Float) {
                moveable.rise((Float) args[0]);
            } else {
                moveable.rise(1f);
            }
        }
    },
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
    ROTATE  {
        @Override
        public void act(Moveable moveable, Object ... args) {
            if (args.length == 3) {
                moveable.rotate((Float)args[0], (Float)args[1], (Float)args[2]);
            }
        }
    },
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
