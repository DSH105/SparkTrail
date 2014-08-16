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

package sparktrailv4;

import com.dsh105.commodus.logging.Log;

public class SparkTrail {

    private static SparkTrailCore CORE;
    public static Log LOG;

    public static void setCore(SparkTrailCore plugin) {
        if (CORE != null) {
            throw new RuntimeException("Core already set!");
        }
        CORE = plugin;
        LOG = new Log("SparkTrail");
    }

    public static SparkTrailCore getCore() {
        return CORE;
    }


}