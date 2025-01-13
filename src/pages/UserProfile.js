import React, { useState, useEffect } from 'react';
import { Container, Card, Row, Col, Button, Tabs, Tab } from 'react-bootstrap';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { postsAPI } from '../api/posts';
import axios from 'axios';
import '../styles/UserProfile.css';

function UserProfile() {
    const { username } = useParams();
    const navigate = useNavigate();
    const { user } = useAuth();
    const [userInfo, setUserInfo] = useState(null);
    const [posts, setPosts] = useState([]);
    const [followers, setFollowers] = useState([]);
    const [following, setFollowing] = useState([]);
    const [isFollowing, setIsFollowing] = useState(false);
    const [activeTab, setActiveTab] = useState('posts');

    const fetchUserData = async () => {
        try {
            console.log('Fetching data for username:', username);
            
            // 유저가 작성한 게시글 목록 (닉네임 정보를 위해 먼저 호출)
            const postsResponse = await postsAPI.getUserPosts(username);
            console.log('Posts response:', postsResponse);
            
            // user 배열에서 닉네임 정보 가져오기
            if (postsResponse.data && postsResponse.data.user && postsResponse.data.user.length > 0) {
                setUserInfo({
                    id: username,
                    nickname: postsResponse.data.user[0].nickname
                });
            }

            // 게시글 설정
            if (postsResponse.data && postsResponse.data.post && postsResponse.data.post.content) {
                setPosts(postsResponse.data.post.content);
            } else {
                setPosts([]);
            }
            
            // 팔로워/팔로잉 정보 가져오기
            const userResponse = await postsAPI.getUserInfo(username);
            console.log('User info response:', userResponse.data);
            setUserInfo(prev => ({
                ...prev,
                following: userResponse.data.following,
                followers: userResponse.data.followers
            }));

            // 팔로워/팔로잉 목록
            if (user) {
                const followersResponse = await postsAPI.getFollowers(username);
                const followingResponse = await postsAPI.getFollowing(username);
                setFollowers(followersResponse.data.users || []);
                setFollowing(followingResponse.data.users || []);

                const followStatus = await postsAPI.checkFollowStatus(user.id, username);
                setIsFollowing(followStatus);
            }
        } catch (error) {
            console.error('Error fetching user data:', error);
            setUserInfo({ nickname: username });
            setPosts([]);
            setFollowers([]);
            setFollowing([]);
        }
    };

    useEffect(() => {
        if (username) {
            fetchUserData();
        }
    }, [username, user]);

    const handleFollow = async () => {
        if (!user) {
            alert('로그인이 필요합니다.');
            return;
        }
        
        try {
            const response = await postsAPI.followUser(username);
            if (response.status === 200) {
                const newIsFollowing = !isFollowing;
                setIsFollowing(newIsFollowing);
                
                // 팔로워 수 업데이트를 위해 유저 정보 다시 가져오기
                const userResponse = await postsAPI.getUserInfo(username);
                setUserInfo(prev => ({
                    ...prev,
                    followers: userResponse.data.followers
                }));
                
                alert(newIsFollowing ? '팔로우 되었습니다.' : '팔로우가 취소되었습니다.');
            }
        } catch (error) {
            console.error('Error following user:', error);
            alert('팔로우 처리 중 오류가 발생했습니다.');
        }
    };

    return (
        <Container className="user-profile-container">
            <Card className="user-info-card mb-4">
                <Card.Body>
                    <Row>
                        <Col md={8}>
                            <h2>{userInfo?.nickname || username}</h2>
                            <div className="user-stats">
                                <span>게시글 {posts.length}</span>
                                <span>팔로워 {userInfo?.followers || 0}</span>
                                <span>팔로잉 {userInfo?.following || 0}</span>
                            </div>
                        </Col>
                        <Col md={4} className="text-end">
                            {user && user.id !== username && (
                                <Button
                                    variant={isFollowing ? "secondary" : "primary"}
                                    onClick={handleFollow}
                                >
                                    {isFollowing ? '팔로잉' : '팔로우'}
                                </Button>
                            )}
                        </Col>
                    </Row>
                </Card.Body>
            </Card>

            <Tabs activeKey={activeTab} onSelect={(k) => setActiveTab(k)} className="mb-4">
                <Tab eventKey="posts" title="작성한 게시글">
                    <Row xs={1} md={2} className="g-4">
                        {posts.map(post => (
                            <Col key={post.postId}>
                                <Card className="list-item">
                                    <Card.Body>
                                        <Link to={`/community/${post.postId}`}>
                                            <Card.Title>{post.title}</Card.Title>
                                        </Link>
                                        <Card.Text>
                                            작성일: {new Date(post.created).toLocaleDateString()}<br />
                                            조회수: {post.cnt}<br />
                                            좋아요: {post.heart}
                                        </Card.Text>
                                    </Card.Body>
                                </Card>
                            </Col>
                        ))}
                    </Row>
                </Tab>

                <Tab eventKey="followers" title="팔로워">
                    <Row xs={1} md={2} className="g-4">
                        {followers.map(follower => (
                            <Col key={follower.id}>
                                <Card className="list-item">
                                    <Card.Body>
                                        <Link to={`/user/${follower.id}`}>
                                            <Card.Title>{follower.nickname}</Card.Title>
                                        </Link>
                                    </Card.Body>
                                </Card>
                            </Col>
                        ))}
                    </Row>
                </Tab>

                <Tab eventKey="following" title="팔로잉">
                    <Row xs={1} md={2} className="g-4">
                        {following.map(follow => (
                            <Col key={follow.id}>
                                <Card className="list-item">
                                    <Card.Body>
                                        <Link to={`/user/${follow.id}`}>
                                            <Card.Title>{follow.nickname}</Card.Title>
                                        </Link>
                                    </Card.Body>
                                </Card>
                            </Col>
                        ))}
                    </Row>
                </Tab>
            </Tabs>
        </Container>
    );
}

export default UserProfile;