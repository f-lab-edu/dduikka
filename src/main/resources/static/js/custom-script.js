var stompClient = null;
var eid = null;

function setConnected(connected) {

    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
    document.getElementById('response').innerHTML = '';
}

function connect() {
    var socket = new SockJS('/chats');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/messages', function (messageOutput) {
                console.log(messageOutput);
                showMessageOutput(JSON.parse(messageOutput.body));
            });

            stompClient.subscribe('/user/queue/chats', function (messageOutput) {
                console.log(messageOutput);
                showList(JSON.parse(messageOutput.body));
            });

            // 최초 로딩 시 목록을 조회해온다
            findAllLiveChat();
        },
        // 연결 실패 시의 콜백 함수
        function (error) {
            var result = document.getElementById('connect-error');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.appendChild(document.createTextNode('Connection failed : 로그인이 필요합니다.'));
            result.appendChild(p);
        }
    );
}

function disconnect() {

    if (stompClient != null) {
        stompClient.disconnect();
    }

    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    var text = document.getElementById('text').value;
    stompClient.send("/app/chat", {}, JSON.stringify({'text': text}));
    document.getElementById('text').value = '';
}

function getMinId() {
    var elements = document.getElementsByClassName('liveChatId');
    var min = 999999;

    if (elements.length === 0) {
        return min; // 최초 조회 시, 기준이 될 임의의 최댓값
    }

    for (var i = 0; i < elements.length; i++) {
        var element = elements[i];
        var value = parseInt(element.textContent);

        if (!isNaN(value) && value < min) {
            min = value;
        }
    }

    return min;
}

function findAllLiveChat() {
    stompClient.send("/app/list/" + getMinId(), {});
}

function deleteLiveChat(data) {
    console.log(data);
    stompClient.send("/app/chat/" + data, {});
}

function showMessageOutput(messageOutput) {
    var response = document.getElementById('response');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode(messageOutput.liveChatId + " : " + messageOutput.text + " (" + messageOutput.time + ")"));
    response.appendChild(p);
}

function showList(messageOutputList) {
    var response = document.getElementById('response');

    var liveChatsResponseList = messageOutputList.liveChatsResponseList;

    liveChatsResponseList.forEach(function (messageOutput) {
        var span = document.createElement('span');
        span.classList.add('liveChatId');
        span.appendChild(document.createTextNode(messageOutput.liveChatId));

        var p = document.createElement('p');
        p.appendChild(span);
        p.appendChild(document.createTextNode(" : " + messageOutput.text + " (" + messageOutput.time + ")"));

        if (messageOutput.eid === eid) {
            // 버튼 요소 생성
            var button = document.createElement('button');
            button.innerHTML = 'X'; // 버튼에 표시될 텍스트 설정

            // 버튼 클릭 이벤트를 처리할 함수 정의 및 할당
            button.onclick = function () {
                deleteLiveChat(messageOutput.liveChatId);
            };

            // 생성된 버튼을 p 태그에 추가
            p.appendChild(button);
        }

        // p 태그를 문서에 추가
        response.insertBefore(p, response.firstChild);
    });
}

function getCookie(name) {
    var value = "; " + document.cookie;
    var parts = value.split("; " + name + "=");
    if (parts.length == 2) return parts.pop().split(";").shift();
}

function login() {
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;
    // 서버로 전송할 데이터 객체를 생성합니다.
    var data = {
        email: email,
        password: password
    };

    // POST 요청을 보내기 위한 설정을 합니다.
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/login', true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    // 요청이 완료되었을 때의 동작을 정의합니다.
    xhr.onload = function () {
        if (xhr.status === 200) {
            // 요청이 성공일 때 로그인 폼을 보이지 않게 하고 초기화합니다.
            document.getElementById('loginDiv').style.display = "none";
            document.getElementById('login').style.display = "none";
            document.getElementById('logout').style.display = "block";
            email.value = '';
            password.value = '';
            console.log('login success');
            eid = getCookie("EID");
            console.log(eid);
        } else {
            // 요청이 실패한 경우의 동작을 작성합니다.
            console.log('login fail');
        }
    };

    // 데이터를 JSON 형식으로 변환하여 전송합니다.
    xhr.send(JSON.stringify(data));
}

function logout() {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/logout', true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function () {
        if (xhr.status === 200) {
            // 요청이 성공일 때 로그인 폼을 보이게 합니다.
            document.getElementById('loginDiv').style.display = "block";
            document.getElementById('login').style.display = "block";
            document.getElementById('logout').style.display = "none";
            console.log('logout success');
            eid = null;
        }
    }
    xhr.send();
}
