import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';

class Player {
  constructor(props) {
    this.state = {usesX: false};
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
    this.state.player = player;
    this.forceUpdate();
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
    this.topLeftSquare = React.createRef();
    this.xPlayer = new Player();
  }

  handleSquareClick(row, column) {
    alert("Square clicked in row " + row + " and column " + column);

    if ((row == 0) && (column == 0)) {
      this.topLeftSquare.current.setPlayer(this.xPlayer);
    }
  }

  renderSquare(row, column, ref) {
    return ( <Square row={row} column={column} ref={ref} onClick={(r, c) => this.handleSquareClick(r, c)}/> );
  }

  render() {
    const status = 'Next player: X';

    return (
      <div>
        <div className="status">{status}</div>
        <div className="board-row">
          {this.renderSquare(0, 0, this.topLeftSquare)}
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
  render() {
    return (
      <div className="game">
        <div className="game-board">
          <Board />
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

