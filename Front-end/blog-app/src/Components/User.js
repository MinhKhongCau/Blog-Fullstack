import React, { useState, useEffect } from 'react';

function PostList() {
  const [posts, setPosts] = useState([]);
  const [error, setError] = useState(null);

  const baseUrl = "http://localhost:8080/demo"
  useEffect(() => {
    fetch(baseUrl, {
      method: "GET",
      mode: "cors"
    })
      .then((response) => {
        // posts = response.text()
        console.log('Data:', response.text())
        // return posts
      })
      .then((data) => {
        setPosts(data)
      })
      .catch((error) => {
        setError(error)
        console.error("Error.in.then: ", error.message)
      });
  }, []);
  if (error) return <p style={{ color: 'red' }}>Error: {error}</p>;

  return (
    <div>
      <h2>Posts from JSONPlaceholder</h2>
      <ul>
        {posts}
      </ul>
    </div>
  );
}

export default PostList;
