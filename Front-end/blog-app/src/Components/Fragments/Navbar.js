import React from 'react';
import '../../assets/css/style.css'
import logo_blog from '../../assets/image/logo.png'
import search from '../../assets/image/search.png'

function Navbar () {
    return (
        <div className='navbar'>
            <img src={logo_blog} alt='' className='logo'/>
            <ul>
                <li>Trang chủ</li>
                <li>Thêm bài viết</li>
                <li>Tài khoản</li>
                <li>Đăng xuất</li>
            </ul>
            <div className='search-box'>
                <input type='text' placeholder='Tìm kiếm bài viết'/>
                <img src={search} alt='' className='search'/>
            </div>
        </div>
    )
}

export default Navbar