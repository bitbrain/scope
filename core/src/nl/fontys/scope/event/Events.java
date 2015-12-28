package nl.fontys.scope.event;

import net.engio.mbassy.bus.MBassador;

public final class Events {

    private static final Events INSTANCE = new Events();

    private MBassador bus;

    private Events() {
        bus = new MBassador();
    }

    public static Events getInstance() {
        return INSTANCE;
    }

    public void fire(String type, Object primary, Object ... secondaries) {
        bus.publish(new GdxEvent(type, primary, secondaries));
    }

    public void register(Object object) {
        bus.subscribe(object);
    }

    public void unregister(Object object) {
        bus.unsubscribe(object);
    }

    public void clear() {
        bus.shutdown();
        bus = new MBassador();
    }

    public static class GdxEvent {

        private Object primary;

        private Object[] secondaries;

        private String type;

        private GdxEvent(String type, Object primaryParameter, Object ... secondaryParameters) {
            this.type = type;
            this.primary = primaryParameter;
            this.secondaries = secondaryParameters;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isTypeOf(String type) {
            return this.type.equals(type);
        }

        public Object getPrimaryParam() {
            return primary;
        }

        public boolean hasPrimaryParam() {
            return primary != null;
        }

        public Object getSecondaryParam(int index) {
            if (hasSecondaryParam(index)) {
                return secondaries[index];
            } else {
                return null;
            }
        }

        public boolean hasSecondaryParams() {
            return secondaries != null && secondaries.length > 0;
        }

        public boolean hasSecondaryParam(int index) {
            return hasSecondaryParams() && index >= 0 && index < secondaries.length;
        }
    }
}