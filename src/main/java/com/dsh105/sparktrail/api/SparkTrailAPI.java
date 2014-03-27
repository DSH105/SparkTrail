/*
 * This file is part of SparkTrail 3.
 *
 * SparkTrail 3 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SparkTrail 3 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SparkTrail 3.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.dsh105.sparktrail.api;

import com.dsh105.sparktrail.data.EffectManager;
import com.dsh105.sparktrail.trail.EffectHolder;

import java.util.HashSet;


public class SparkTrailAPI {

    private LocationAPI locationAPI;
    private MobAPI mobAPI;
    private PlayerAPI playerAPI;

    public SparkTrailAPI() {
        this.locationAPI = new LocationAPI();
        this.mobAPI = new MobAPI();
        this.playerAPI = new PlayerAPI();
    }

    public LocationAPI locationAPI() {
        return this.locationAPI;
    }

    public MobAPI mobAPI() {
        return this.mobAPI;
    }

    public PlayerAPI playerAPI() {
        return this.playerAPI;
    }

    public HashSet<EffectHolder> getAllActiveEffects() {
        return EffectManager.getInstance().getEffectHolders();
    }
}