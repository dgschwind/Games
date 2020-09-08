import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';

class Square extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <button className="square" onClick={this.props.clickHandler}>
        {this.props.owner}
      </button>
    );
  }
}

class Board extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
        squareOwners: new Array(9).fill(null),
        nextPlayer: 'X',
        winner: null,
    };
  }

  // Determines the winner of the game, if there is one.
  // Returns null to indicate there is no winner yet.
  determineGameWinner(squareOwners) {
    var linesToCheck = [
      [0,1,2],
      [3,4,5],
      [6,7,8],
      [0,3,6],
      [1,4,7],
      [2,5,8],
      [0,4,8],
      [2,4,6]];

    for (var lineIndex = 0;lineIndex < linesToCheck.length;lineIndex++) {
      var lineToCheck = linesToCheck[lineIndex];
      var gameWinner = squareOwners[lineToCheck[0]];

      if (gameWinner && 
          (squareOwners[lineToCheck[1]] == gameWinner) &&
          (squareOwners[lineToCheck[2]] == gameWinner)) {
         return gameWinner;
      }
    }

    // Either no winner as yet, or the game is a tie.
    return null;
  }

  handleCellClick(i) {
    if (this.state.squareOwners[i]) {
      // One of the players already owns this square, ignore this click.
      return;
    }
    if (this.state.winner) {
      // Ignore this click, the game has already been won.
      return;
    }

    var squareOwnersCopy = this.state.squareOwners.slice();
    squareOwnersCopy[i] = this.state.nextPlayer;
    var gameWinner = this.determineGameWinner(squareOwnersCopy);
    this.setState({
       squareOwners: squareOwnersCopy,
       nextPlayer: this.state.nextPlayer === 'X' ? 'O' : 'X',
       winner: gameWinner,
    });
  }

  renderSquare(i) {
    return <Square owner={this.state.squareOwners[i]} clickHandler={() => this.handleCellClick(i)}/>;
  }

  playAgain() {
    this.setState({
       squareOwners: new Array(9).fill(null),
       nextPlayer: this.state.winner ? this.state.winner : 'X',
       winner: null,
    });
  }

  render() {
    var status;
    if (this.state.winner) {
      status = 'Winner: ' + this.state.winner;
    } else {
      status = 'Next player: ' + this.state.nextPlayer;
    }

    return (
      <div>
        <div className="status">{status}</div>
        <div className="board-row">
          {this.renderSquare(0)}
          {this.renderSquare(1)}
          {this.renderSquare(2)}
        </div>
        <div className="board-row">
          {this.renderSquare(3)}
          {this.renderSquare(4)}
          {this.renderSquare(5)}
        </div>
        <div className="board-row">
          {this.renderSquare(6)}
          {this.renderSquare(7)}
          {this.renderSquare(8)}
        </div>
        {this.state.winner &&
          <div>
            <button onClick={() => this.playAgain()}>Play Again?</button>
          </div>
        }
      </div>
    );
  }
}

class Game extends React.Component {
  render() {
    return (
      <div className="game">
        <div className="game-board">
          <Board />
        </div>
        <div className="game-info">
          <div>{/* status */}</div>
          <ol>{/* TODO */}</ol>
        </div>
      </div>
    );
  }
}

// ========================================

ReactDOM.render(
  <Game />,
  document.getElementById('root')
);
