package lotto.domain.ticket;

import lotto.domain.number.LottoNumber;
import lotto.domain.number.TestLottoNumberFactory;
import lotto.domain.result.LottoStatistics;
import lotto.domain.result.Rank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LottoTicketsTest {

    private static final int LOTTO_TICKET_PRICE = 1000;
    private LottoTickets lottoTickets;
    private WinningLotto winningLotto;

    @BeforeEach
    void setup() {
        List<LottoTicket> tickets = List.of(
                LottoTicket.of(
                        TestLottoNumberFactory.createLottoNumbers(1, 2, 3, 4, 5, 6)
                ),
                LottoTicket.of(
                        TestLottoNumberFactory.createLottoNumbers(7, 8, 9, 10, 11, 12)
                )
        );

        lottoTickets = LottoTickets.of(tickets);

        winningLotto = WinningLotto.of(
                LottoTicket.of(
                        TestLottoNumberFactory.createLottoNumbers(1, 2, 3, 4, 5 ,6)
                ),
                LottoNumber.of(7)
        );
    }

    @DisplayName("다수의 로또 티켓을 생성할 수 있다.")
    @Test
    void createMultipleLottoTickets() {
        assertThat(lottoTickets.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("당첨 결과에 따른 순위 집계 테스트")
    void countRanksByWinningLotto() {
        LottoStatistics lottoStatistics = lottoTickets.generateLottoStatistics(winningLotto, LOTTO_TICKET_PRICE);
        Map<Rank, Integer> rankCounts = lottoStatistics.getResults();

        assertThat(rankCounts).containsEntry(Rank.FIRST, 1);
        assertThat(rankCounts).containsEntry(Rank.NONE, 1);
    }
}
