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

package sparktrailv4.trail;

import com.dsh105.commodus.IdentUtil;
import org.bukkit.entity.Player;

public class PlayerTrail extends EntityTrail<Player> {

    private String playerIdent;

    public PlayerTrail(Player entity) {
        super(entity);
        this.playerIdent = IdentUtil.getIdentificationForAsString(entity);
    }

    public PlayerTrail(int tickInterval, Player entity) {
        super(tickInterval, entity);
        this.playerIdent = IdentUtil.getIdentificationForAsString(entity);
    }

    @Override
    public Player getEntity() {
        return IdentUtil.getPlayerOf(playerIdent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PlayerTrail that = (PlayerTrail) o;

        return playerIdent.equals(that.playerIdent);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + playerIdent.hashCode();
        return result;
    }
}