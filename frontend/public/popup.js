document.addEventListener('DOMContentLoaded', function() {
  const saveBookmarkBtn = document.getElementById('saveBookmarkBtn');
  const tagContainer = document.getElementById('tagContainer');
  const bookmarkList = document.getElementById('bookmarkList');

  // 북마크 저장 버튼 클릭 시 GPT API 호출 후 북마크 저장
  saveBookmarkBtn.addEventListener('click', function() {
    chrome.tabs.query({ active: true, currentWindow: true }, function(tabs) {
      const currentTab = tabs[0];
      const bookmark = {
        title: currentTab.title,
        url: currentTab.url
      };

      // GPT API 호출하여 태그 생성
      fetchGPTTags(bookmark.url, bookmark.title)
        .then((tags) => {
          bookmark.tags = tags; // GPT에서 생성된 태그 추가

          // 기존 북마크 불러오기 및 새로운 북마크 저장
          chrome.storage.sync.get({ bookmarks: [] }, function(data) {
            const bookmarks = data.bookmarks;
            bookmarks.push(bookmark);

            chrome.storage.sync.set({ bookmarks: bookmarks }, function() {
              displayTags(); // 태그 목록 업데이트
              displayBookmarks(bookmarks); // 북마크 목록 업데이트
            });
          });
        })
        .catch((error) => {
          console.error('GPT API 호출 중 오류 발생:', error);
        });
    });
  });

  function fetchGPTTags(url, title) {
    const apiKey = config.OPENAI_API_KEY;
    const apiUrl = 'https://api.openai.com/v1/chat/completions';
  
    const prompt = `Generate 6 tags in korean for the following website based on its URL and title: URL: ${url}, Title: ${title}`;
  
    return fetch(apiUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${apiKey}`
      },
      body: JSON.stringify({
        model: 'gpt-3.5-turbo',
        messages: [
          { role: 'system', content: 'You are an assistant that generates tags for websites.' },
          { role: 'user', content: prompt }
        ],
        max_tokens: 50
      })
    })
    .then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return response.json();
    })
    .then(data => {
      if (data.choices && data.choices.length > 0 && data.choices[0].message.content) {
        const tags = data.choices[0].message.content.trim().split(',').slice(0, 6);
        return tags;
      } else {
        throw new Error('GPT 응답에서 태그를 생성할 수 없습니다.');
      }
    })
    .catch(error => {
      console.error('GPT API 호출 중 오류 발생:', error);
      return [];
    });
  }
  
  
  
  // 모든 태그 표시
  function displayTags() {
    chrome.storage.sync.get({ bookmarks: [] }, function(data) {
      const bookmarks = data.bookmarks;
      const tagSet = new Set(); // 태그 중복 방지

      bookmarks.forEach(bookmark => {
        bookmark.tags.forEach(tag => tagSet.add(tag));
      });

      tagContainer.innerHTML = ''; // 기존 태그 목록 초기화
      tagSet.forEach(tag => {
        const tagElement = document.createElement('span');
        tagElement.className = 'tag';
        tagElement.textContent = tag;
        tagElement.addEventListener('click', function() {
          filterBookmarksByTag(tag); // 태그 클릭 시 해당 북마크 필터링
        });
        tagContainer.appendChild(tagElement);
      });
    });
  }

  // 태그로 북마크 필터링
  function filterBookmarksByTag(tag) {
    chrome.storage.sync.get({ bookmarks: [] }, function(data) {
      const bookmarks = data.bookmarks.filter(bookmark => bookmark.tags.includes(tag));
      displayBookmarks(bookmarks);
    });
  }

  // 북마크 목록 표시
  function displayBookmarks(bookmarks) {
    bookmarkList.innerHTML = ''; // 기존 목록 초기화
    bookmarks.forEach(function(bookmark) {
      const listItem = document.createElement('li');
      listItem.innerHTML = `<a href="${bookmark.url}" target="_blank">${bookmark.title}</a><p>Tags: ${bookmark.tags.join(', ')}</p>`;
      bookmarkList.appendChild(listItem);
    });
  }

  // 초기화 시 태그 목록과 북마크 목록 표시
  displayTags();
  chrome.storage.sync.get({ bookmarks: [] }, function(data) {
    displayBookmarks(data.bookmarks);
  });
});
