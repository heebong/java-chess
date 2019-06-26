package chess.controller;

import chess.dao.ChessDao;
import chess.domain.board.DefaultPlayer;
import chess.domain.board.Game;
import chess.domain.board.Player;
import chess.domain.board.PlayerFactory;
import chess.domain.dto.BoardDto;
import chess.domain.dto.HistoryDto;
import chess.domain.piece.BlackPieceInit;
import chess.domain.piece.PieceColor;
import chess.domain.piece.WhitePieceInit;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.List;

public class InitController {
    public static Object init(Request request, Response response) throws SQLException {
        Player whitePlayer = new DefaultPlayer(PlayerFactory.init(new WhitePieceInit()));
        Player blackPlayer = new DefaultPlayer(PlayerFactory.init(new BlackPieceInit()));
        Game game = new Game(whitePlayer, blackPlayer);

        if (ChessDao.notEnd()) {
            int round = ChessDao.getRound();
            request.session().attribute("round", round);
            List<HistoryDto> list = ChessDao.getHistory(round);

            for (HistoryDto dto : list) {
                game.move(dto.getSrc(), dto.getTrg());
            }

            BoardDto boardDto = new BoardDto(whitePlayer, blackPlayer, game.getTurn(),
                    game.getDeadList(PieceColor.WHITE), game.getDeadList(PieceColor.BLACK));

            request.session().attribute("game", game);
            return new Gson().toJson(boardDto);
        }

        int round = ChessDao.getRound() + 1;
        request.session().attribute("round", round);

        ChessDao.addRound(round);

        BoardDto boardDto = new BoardDto(whitePlayer, blackPlayer, game.getTurn(),
                game.getDeadList(PieceColor.WHITE), game.getDeadList(PieceColor.BLACK));

        request.session().attribute("game", game);
        return new Gson().toJson(boardDto);
    }
}
