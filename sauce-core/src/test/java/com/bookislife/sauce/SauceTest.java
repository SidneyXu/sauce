package com.bookislife.sauce;

import com.bookislife.sauce.files.FileHandles;
import com.bookislife.sauce.mock.MockFileHandles;
import com.bookislife.sauce.mock.MockProvider;
import com.bookislife.sauce.providers.Providers;
import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by SidneyXu on 2015/10/23.
 */
public class SauceTest extends TestCase {

    public void testInitialize() throws Exception {
        final MockFileHandles mockFileHandles = new MockFileHandles();
        final MockProvider mockProvider = new MockProvider();
        SaucePlatform mockPlatform = new SaucePlatform() {
            @Override
            protected FileHandles getFiles() {
                return mockFileHandles;
            }

            @Override
            public Providers getProviders(final String type) {
                return mockProvider;
            }
        };
        Sauce.initialize(mockPlatform);

        assertThat(Sauce.files == mockFileHandles);
        assertThat(Sauce.getPlatform() == mockPlatform);
    }
}