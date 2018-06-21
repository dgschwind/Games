import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';

class Player {
  constructor(props) {
    this.state = {usesX: false};
  }

  setUsesX() {
    this.state.usesX = true;
  }

  getSymbol() {
    return this.state.usesX ? "X" : "O";
  }
}

class Square extends React.Component {
  constructor(props) {
    super(props);
    this.player = null;
  }

  setPlayer(player) {
    if (this.player === null) {
      this.player = player;
      // Force the square to be re-rendered
      this.forceUpdate();
    }
  }

  clear() {
    this.player = null;
    this.forceUpdate();
  }

  hasBeenSelected() {
    return this.player != null;
  }

  render() {
    let squareContents = "";
    if (this.player != null) {
      squareContents = this.player.getSymbol();
    }

    return (
      <button className="square" onClick={() => this.props.onClick(this.props.row, this.props.column)}>
        {squareContents}
      </button>
    );
  }
}

class Board extends React.Component {
  constructor(props) {
    super(props);
    this.xPlayer = this.props.xPlayer;
    this.oPlayer = this.props.oPlayer;
    this.currentPlayer = this.xPlayer;
    this.squares = [[React.createRef(), React.createRef(), React.createRef()],
                    [React.createRef(), React.createRef(), React.createRef()],
                    [React.createRef(), React.createRef(), React.createRef()]];
    this.setStatus();

    this.handleGameEndedWithNoWinner = this.handleGameEndedWithNoWinner.bind(this);
  }

  setStatus() {
    this.status = "Next Player : " + this.currentPlayer.getSymbol();
  }

  updateStatus() {
    this.setStatus();
    this.forceUpdate();
  }

  clear() {
    for (var row = 0;row < 3;row++) {
      for (var column = 0;column < 3;column++) {
        this.squares[row][column].current.clear();
      }
    }
  }

  handleGameEndedWithNoWinner() {
    if (window.confirm("Game has ended with no winner. Play again?")) {
      this.clear();
    }
  }

  handleSquareClick(row, column) {
    this.squares[row][column].current.setPlayer(this.currentPlayer);
    if (this.currentPlayer === this.xPlayer) {
      this.currentPlayer = this.oPlayer;
    } else {
      this.currentPlayer = this.xPlayer;
    }
    this.updateStatus();

    if (this.isFull()) {
      setTimeout(this.handleGameEndedWithNoWinner, 200);
    }
  }

  renderSquare(row, column) {
    var localRef = this.squares[row][column];
    return ( <Square row={row} column={column} ref={localRef} onClick={(r, c) => this.handleSquareClick(r, c)}/> );
  }

  render() {
    return (
      <div>
        <div className="status">{this.status}</div>
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

  isFull() {
    for (var row = 0;row < 3;row++) {
      for (var column = 0;column < 3;column++) {
        if (!this.squares[row][column].current.hasBeenSelected()) {
          return false;
        }
      }
    }
    return true;
  }
}

class TicTacToe extends React.Component {
  constructor(props) {
    super(props);
    this.xPlayer = new Player();
    this.xPlayer.setUsesX();
    this.oPlayer = new Player();
  }

  render() {
    return (
      <div className="game">
        <div className="game-board">
          <Board xPlayer={this.xPlayer} oPlayer={this.oPlayer} />
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

