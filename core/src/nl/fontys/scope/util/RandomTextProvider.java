package nl.fontys.scope.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nl.fontys.scope.i18n.Bundle;
import nl.fontys.scope.i18n.Messages;

public class RandomTextProvider {

    private List<String> keys;

    private SecureRandom random;

    public RandomTextProvider() {
        keys = new ArrayList<String>();
        random = new SecureRandom(UUID.randomUUID().toString().getBytes());
        setupKeys();
    }

    public String getNextText() {
        int index = getNextIndex();
        String key = keys.size() > 1 ? keys.remove(index) : keys.get(index);
        return Bundle.general.get(key);
    }

    private int getNextIndex() {
        return (int)Math.floor(random.nextFloat() * keys.size());
    }

    private void setupKeys() {
        keys.add(Messages.RANDOM_TEXT_01);
        keys.add(Messages.RANDOM_TEXT_02);
        keys.add(Messages.RANDOM_TEXT_03);
        keys.add(Messages.RANDOM_TEXT_04);
        keys.add(Messages.RANDOM_TEXT_05);
        keys.add(Messages.RANDOM_TEXT_06);
        keys.add(Messages.RANDOM_TEXT_07);
        keys.add(Messages.RANDOM_TEXT_08);
        keys.add(Messages.RANDOM_TEXT_09);

    }
}
