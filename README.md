# java-chess

체스 미션 저장소

### 기능 요구 사항

## Space

- [x] 각 Space는 Position을 가지고 있다.
- [x] 각 Space는 피스 또는 빈칸을 가지고 있다.
- [x] Space는 피스를 움직일 수 있다.
    - [x] 이동할 위치에 나의 말이 있으면 이동할 수 없다
    - [x] 이동할 위치에 상대 말이 있고 잡을 수 있으면 이동할 수 있다.
    - [x] 각 피스의 이동규칙을 위반 할 수 없다

## 위치

- [x] 각 위치는 file(a~h) 과 rank(1~8)로 구성되어 있다.

## 피스

- [x] 각 피스 종류마다 나타내는 기호가 있다.
- [x] 피스는 검정색 혹은 하얀색을 가지고 있다.
- [x] 피스끼리 같은색인지 구분할 수 있다.

## 피스 규칙

- [ ] Pawn
    - [ ] 첫 이동시 한칸 또는 두칸을 전진할 수 있다.
    - [x] 그 외에는 한칸씩만 전진 할 수 있다.
    - [x] 폰은 자신의 진행방향의 대각선 방향 피스만 잡을 수 있다.
    - [x] 검정은 아래로만 전진가능하다. 흰색은 위로만 전진 가능하다.
- [x] Rook
    - [x] 상하좌우로 칸 수 제한 없이 움직일 수 있다.
    - [x] 피스를 뛰어 넘어 움직일 수 없다.
    - [x] 이동할 위치에 상대피스가 있으면 잡을 수 있다.
- [x] Knight
    - [x] 한칸 이동+한칸 대각선 이동만 가능하다.
    - [x] 피스를 뛰어 넘어 움직일 수 있다.
    - [x] 이동할 위치에 상대피스가 있으면 잡을 수 있다.
- [x] Bishop
    - [x] 대각선 방향으로 칸 수 제한 없이 움직일 수 있다.
    - [x] 피스를 뛰어 넘어 움직일 수 없다.
    - [x] 이동할 위치에 상대피스가 있으면 잡을 수 있다.
- [x] Queen
    - [x] 상하좌우, 대각선 방향으로 칸 수 제한 없이 움직일 수 있다.
    - [x] 피스를 뛰어 넘어 움직일 수 없다.
    - [x] 이동할 위치에 상대피스가 있으면 잡을 수 있다.
- [x] King
    - [x] 상하좌우, 대각선 방향으로 한 칸 움직일 수 있다.
    - [x] 피스를 뛰어 넘어 움직일 수 없다.
    - [x] 이동할 위치에 상대피스가 있으면 잡을 수 있다.

# 게임 진행
- [ ] 게임 시작 안내 문구 출력
- [ ] 게임 조작법 안내 문구 출력
- [ ] 사용자 입력에 따라 게임을 진행한다.
  - [ ] 게임 종료 커맨드 입력 전까지 반복한다.
