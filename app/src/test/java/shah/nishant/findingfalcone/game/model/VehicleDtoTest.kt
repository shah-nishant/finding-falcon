package shah.nishant.findingfalcone.game.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class VehicleDtoTest {

    @Test
    fun `should return true when availableNumber is greater than 0 and can reach the planet`() {
        //given
        val dto = VehicleDto(Vehicle("Falcon", 1, 100, 2), 1, true)

        //when
        val result = dto.isSelectable()

        //then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return true when availableNumber is not greater than 0`() {
        //given
        val dto = VehicleDto(Vehicle("Falcon", 1, 100, 2), 0, true)

        //when
        val result = dto.isSelectable()

        //then
        assertThat(result).isFalse()
    }

    @Test
    fun `should return true when vehicle can not reach the planet`() {
        //given
        val dto = VehicleDto(Vehicle("Falcon", 1, 100, 2), 1, false)

        //when
        val result = dto.isSelectable()

        //then
        assertThat(result).isFalse()
    }
}