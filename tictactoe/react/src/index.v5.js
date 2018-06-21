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

    this.state = {row: this.props.row,
                  column: this.props.column,
                  player: null};
  }

  setPlayer(player) {
    if (this.state.player == null) {
      this.state.player = player;
      this.forceUpdate();
    }
  }

  render() {
    let squareContents = "";
    if (this.state.player != null) {
      squareContents = this.state.player.getSymbol();
    }

    return (
      <button className="square" onClick={() => this.props.onClick(this.state.row, this.state.column)}>
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
    this.squares = [[React.createRef(), React.createRef(), React.createRef()],
                    [React.createRef(), React.createRef(), React.createRef()],
                    [React.createRef(), React.createRef(), React.createRef()]];
    this.currentPlayer = this.xPlayer;
  }

  handleSquareClick(row, column) {
    this.squares[row][column].current.setPlayer(this.currentPlayer);
    if (this.currentPlayer === this.xPlayer) {
      this.currentPlayer = this.oPlayer;
    } else {
      this.currentPlayer = this.xPlayer;
    }
  }

  renderSquare(row, column) {
    var localRef = this.squares[row][column];
    return ( <Square row={row} column={column} ref={localRef} onClick={(r, c) => this.handleSquareClick(r, c)}/> );
  }

  render() {
    const status = 'Next player: X';

    return (
      <div>
        <div className="status">{status}</div>
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

