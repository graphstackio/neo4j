/*
 * Copyright (c) "Neo4j"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.kernel.impl.transaction.log.entry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.neo4j.kernel.impl.transaction.log.entry.CheckpointLogVersionSelector.INSTANCE;
import static org.neo4j.kernel.impl.transaction.log.entry.CheckpointParserSetV4_2.V4_2;

class CheckpointLogVersionSelectorTest
{
    @Test
    void selectAnyVersion()
    {
        assertEquals( V4_2, INSTANCE.select( V4_2.versionByte() ) );
    }

    @Test
    void warnAboutOldLogVersion()
    {
        assertThrows( UnsupportedLogVersionException.class, () -> INSTANCE.select( (byte) -4 ) );
    }

    @Test
    void warnAboutNotUsedNegativeLogVersion()
    {
        assertThrows( UnsupportedLogVersionException.class, () -> INSTANCE.select( (byte) -42 ) );
    }

    @Test
    void warnAboutNotUsedPositiveLogVersion()
    {
        assertThrows( UnsupportedLogVersionException.class, () -> INSTANCE.select( (byte) 100 ) );
    }

    @Test
    void checkForMoreRecentVersion()
    {
        assertFalse( INSTANCE.moreRecentVersionExists( V4_2.versionByte() ) );
    }
}
