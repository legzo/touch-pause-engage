import Equipe.*
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class CalculsTest {

    @Test
    fun `calcule le score final d'un match sans points`() {
        val match = Match(
            equipeA = Bordeaux,
            equipeB = Perpignan,
            marques = listOf(),
        )

        with(calculeScore(match)) {
            scoreEquipeA shouldBe 0
            scoreEquipeB shouldBe 0
        }
    }

    @Test
    fun `calcule le score final d'un match`() {
        with(calculeScore(matchPerpignanBordeaux)) {
            scoreEquipeA shouldBe 22
            scoreEquipeB shouldBe 15
        }

        with(calculeScore(matchToulouseBiarritz)) {
            scoreEquipeA shouldBe 80
            scoreEquipeB shouldBe 7
        }

        with(calculeScore(matchClermontMontpellier)) {
            scoreEquipeA shouldBe 20
            scoreEquipeB shouldBe 15
        }

        with(calculeScore(matchLyonLaRochelle)) {
            scoreEquipeA shouldBe 26
            scoreEquipeB shouldBe 29
        }

        with(calculeScore(matchRacing92Toulon)) {
            scoreEquipeA shouldBe 21
            scoreEquipeB shouldBe 16
        }

        with(calculeScore(matchPauCastres)) {
            scoreEquipeA shouldBe 16
            scoreEquipeB shouldBe 26
        }

        with(calculeScore(matchStadeFrancaisBrive)) {
            scoreEquipeA shouldBe 17
            scoreEquipeB shouldBe 33
        }
    }

    @Test
    fun `calcule l'evolution du score d'un match`() {
        calculeEvolutionScore(matchPerpignanBordeaux)
            .map { it.scoreEquipeA to it.scoreEquipeB } shouldBe
                listOf(
                    0 to 0,
                    0 to 7,
                    7 to 7,
                    10 to 7,
                    10 to 12,
                    17 to 12,
                    22 to 12,
                    22 to 15,
                )
    }

    @Test
    fun `calcule l'evolution des points d'un match`() {
        calculeEvolutionScore(matchPerpignanBordeaux)
            .map { it.toPoints() }
            .map { it.pointsEquipeA to it.pointsEquipeB } shouldBe
                listOf(
                    2 to 2,
                    0 to 4,
                    2 to 2,
                    4 to 1,
                    1 to 4,
                    4 to 1,
                    4 to 0,
                    4 to 0,
                )

        with(calculeEvolutionScore(matchToulouseBiarritz)
            .map { it.toPoints() }
            .map { it.pointsEquipeA to it.pointsEquipeB }) {
            first() shouldBe (2 to 2)
            last() shouldBe (5 to 0)
        }
    }


    @Test
    fun `calcule l'evolution du classement`() {
        val classementInitial = Classement(
            equipes = mapOf(
                Bordeaux to PositionClassement(72, 133),
                Perpignan to PositionClassement(39, -180),
                Toulouse to PositionClassement(66, 133),
                Biarritz to PositionClassement(24, -363)
            )
        )
        val evolutionClassement = calculeEvolutionClassement(
            classementInitial, setOf(matchPerpignanBordeaux, matchToulouseBiarritz)
        )

        with(evolutionClassement) {
            first() shouldBe classementInitial
            last() shouldBe Classement(
                equipes = mapOf(
                    Bordeaux to PositionClassement(72, 126),
                    Perpignan to PositionClassement(43, -173),
                    Toulouse to PositionClassement(71, 206),
                    Biarritz to PositionClassement(24, -436)
                )
            )

            last().sorted().map { it.key } shouldBe listOf(
                Bordeaux,
                Toulouse,
                Perpignan,
                Biarritz
            )
        }
    }

    @Test
    fun `calcule l'evolution du classement avec tous les matches`() {
        val classementInitial = Classement(
            equipes = mapOf(
                Montpellier to PositionClassement(73, 121),
                Bordeaux to PositionClassement(72, 133),
                Castres to PositionClassement(72, 26),
                LaRochelle to PositionClassement(67, 171),
                Racing92 to PositionClassement(66, 88),
                Toulouse to PositionClassement(66, 133),
                Toulon to PositionClassement(64, 73),
                Lyon to PositionClassement(63, 82),
                Clermont to PositionClassement(62, 87),
                Pau to PositionClassement(50, -86),
                StadeFrancais to PositionClassement(50, -91),
                Brive to PositionClassement(42, -194),
                Perpignan to PositionClassement(39, -180),
                Biarritz to PositionClassement(24, -363)
            )
        )

        val evolutionClassement = calculeEvolutionClassement(
            classementInitial, setOf(
                matchClermontMontpellier,
                matchLyonLaRochelle,
                matchRacing92Toulon,
                matchPauCastres,
                matchStadeFrancaisBrive,
                matchToulouseBiarritz,
                matchPerpignanBordeaux,
            )
        )

        with(evolutionClassement) {
            last().sorted().map { it.key } shouldBe listOf(
                Castres,
                Montpellier,
                Bordeaux,
                Toulouse,
                LaRochelle,
                Racing92,
                Clermont,
                Toulon,
                Lyon,
                Pau,
                StadeFrancais,
                Brive,
                Perpignan,
                Biarritz
            )
        }

        val stats = calculeStatsEvolutionClassement(
            classementInitial, setOf(
                matchClermontMontpellier,
                matchLyonLaRochelle,
                matchRacing92Toulon,
                matchPauCastres,
                matchStadeFrancaisBrive,
                matchToulouseBiarritz,
                matchPerpignanBordeaux,
            )
        )

        println(stats)
    }
}