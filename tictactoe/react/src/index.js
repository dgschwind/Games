import React from 'react';
import ReactDOM from 'react-dom';
import './tictactoe.css';

class Player {
  constructor(usesX) {
    this.usesX = usesX;
  }

  getSymbol() {
    return this.usesX ? "X" : "O";
  }
}

class Square extends React.Component {
  constructor(props) {
    super(props);
    this.state = {player : null};
  }

  setPlayer(player) {
    if (this.state.player === null) {
      this.setState({player : player});
      return true;
    }
    return false;
  }

  hasBeenSelected() {
    return this.state.player != null;
  }

  hasBeenSelectedByPlayer(currentPlayer) {
    return this.state.player == currentPlayer;
  }

  render() {
    let squareContents = "";
    if (this.state.player != null) {
      squareContents = this.state.player.getSymbol();
    }

    return (
      <button className="square" onClick={() => this.props.onClick(this.props.row, this.props.column)}>
        {squareContents}
      </button>
    );
  }

  clear() {
    this.setState({player : null});
  }
}

class Board extends React.Component {
  constructor(props) {
    super(props);
    this.squares = [[React.createRef(), React.createRef(), React.createRef()],
                    [React.createRef(), React.createRef(), React.createRef()],
                    [React.createRef(), React.createRef(), React.createRef()]];
    this.currentPlayer = null;
  }

  setCurrentPlayer(newValue) {
    this.currentPlayer = newValue;
  }

  currentPlayerHasAllThreeSelected(square1, square2, square3) {
    return (square1.hasBeenSelectedByPlayer(this.currentPlayer) &&
            square2.hasBeenSelectedByPlayer(this.currentPlayer) &&
            square3.hasBeenSelectedByPlayer(this.currentPlayer));
  }

  // Returns the Square object itself
  getSquare(row, column) {
    return this.squares[row][column].current;
  }

  hasCurrentPlayerWonGame() {
    return (this.currentPlayerHasAllThreeSelected(this.getSquare(0,0), this.getSquare(0,1), this.getSquare(0,2)) || // top row
            this.currentPlayerHasAllThreeSelected(this.getSquare(1,0), this.getSquare(1,1), this.getSquare(1,2)) || // middle row
            this.currentPlayerHasAllThreeSelected(this.getSquare(2,0), this.getSquare(2,1), this.getSquare(2,2)) || // bottom row
            this.currentPlayerHasAllThreeSelected(this.getSquare(0,0), this.getSquare(1,0), this.getSquare(2,0)) || // left column
            this.currentPlayerHasAllThreeSelected(this.getSquare(0,1), this.getSquare(1,1), this.getSquare(2,1)) || // middle column
            this.currentPlayerHasAllThreeSelected(this.getSquare(0,2), this.getSquare(1,2), this.getSquare(2,2)) || // right column
            this.currentPlayerHasAllThreeSelected(this.getSquare(0,0), this.getSquare(1,1), this.getSquare(2,2)) || // left diagonal
            this.currentPlayerHasAllThreeSelected(this.getSquare(0,2), this.getSquare(1,1), this.getSquare(2,0)));  // right diagonal
  }

  haveAllSquaresBeenChosen() {
    const N = 3;
    for (var row = 0;row < N;row++) {
      for (var column = 0;column < N;column++) {
        if (!this.getSquare(row,column).hasBeenSelected()) {
          return false;
        }
      }
    }
    return true;
  }

  handleSquareClick(row, column) {
    if (this.getSquare(row,column).setPlayer(this.currentPlayer)) {
      // Wait one millisecond before informing our observer that a given
      // square has been selected to allow that state to be fully propagated
      // into the Square involved.
      setTimeout(this.props.onSquareSelected, 1);
    }
  }

  renderSquare(row, column) {
    var localRef = this.squares[row][column];
    return ( <Square row={row} column={column} ref={localRef} onClick={(r, c) => this.handleSquareClick(r, c)}/> );
  }

  render() {
    return (
      <div>
        <div className="board-row">
          {this.renderSquare(0, 0)}
          {this.renderSquare(0, 1)}
          {this.renderSquare(0, 2)}
        </div>
        <div className="board-row">
          {this.renderSquare(1, 0)}
          {this.renderSquare(1, 1)}
          {this.renderSquare(1, 2)}
        </div>
        <div className="board-row">
          {this.renderSquare(2, 0)}
          {this.renderSquare(2, 1)}
          {this.renderSquare(2, 2)}
        </div>
      </div>
    );
  }

  clear() {
    const N = 3;
    for (var row = 0;row < N;row++) {
      for (var column = 0;column < N;column++) {
        this.getSquare(row,column).clear();
      }
    }
  }
}

class TicTacToe extends React.Component {
  constructor(props) {
    super(props);
    this.state = {nextPlayerStatus : null};
    this.xPlayer = new Player(true);
    this.oPlayer = new Player(false);
    this.currentPlayer = this.xPlayer;
    this.board = null;
  }

  componentDidMount() {
    this.board.setCurrentPlayer(this.currentPlayer);
    this.setNextPlayerStatus();
  }

  changeCurrentPlayer() {
    this.currentPlayer = (this.currentPlayer == this.xPlayer) ? this.oPlayer : this.xPlayer;
    this.setNextPlayerStatus();
    this.board.setCurrentPlayer(this.currentPlayer);
  }

  setNextPlayerStatus() {
    this.setState({nextPlayerStatus : "Next Player : " + this.currentPlayer.getSymbol()});
  }

  handlePlayAgainResponse() {
    if (window.confirm("Play again?")) {
      this.board.clear();
      // Different player starts each game
      this.changeCurrentPlayer();
    }
  }
  
  handleSquareChosen() {
     if (this.board.hasCurrentPlayerWonGame()) {
       alert("Player " + this.currentPlayer.getSymbol() + " has won this game!");
       this.handlePlayAgainResponse();
     } else if (this.board.haveAllSquaresBeenChosen()) {
       alert("Tie! Neither player has won this game");
       this.handlePlayAgainResponse();
     } else {
       this.changeCurrentPlayer();
     }
  }

  render() {
    return (
      <div className="game">
        <div className="game-board">
          <div className="status">{this.state.nextPlayerStatus}</div>
          <Board onSquareSelected={() => this.handleSquareChosen()}
                 ref={(b) => this.board = b} />
        </div>
        <div className="game-info">
          <div>{/* status */}</div>
        </div>
      </div>
    );
  }
}

// ========================================

ReactDOM.render(
  <TicTacToe />,
  document.getElementById('root')
);

