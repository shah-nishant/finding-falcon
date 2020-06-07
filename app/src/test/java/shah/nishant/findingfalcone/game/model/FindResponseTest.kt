package shah.nishant.findingfalcone.game.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FindResponseTest {

    @Test
    fun `should return true when status = success and planet name is not null`() {
        //given
        val findResponse = FindResponse("success", "earth", null)

        //when
        val result = findResponse.isSuccessful()

        //then
        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when status is not success`() {
        //given
        val findResponse = FindResponse("failure", "earth", null)

        //when
        val result = findResponse.isSuccessful()

        //then
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when planet name is null`() {
        //given
        val findResponse = FindResponse("success", null, null)

        //when
        val result = findResponse.isSuccessful()

        //then
        assertThat(result).isFalse()
    }

}