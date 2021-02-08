// @ts-nocheck
import React, { useEffect, useState } from "react";
import { Link, useHistory } from "react-router-dom";
import { AppstoreAddOutlined } from "@ant-design/icons";
import styled from "@emotion/styled/macro";
import { Layout, Menu } from "antd";

const menuList = [
  {
    title: "Clusters",
    id: "cluster",
    icon: <AppstoreAddOutlined />,
    active: true,
  },
  {
    title: "Kubernetes",
    id: "kubernetes",
    icon: <AppstoreAddOutlined />,
    sub: [
      {
        id: "daemonset",
        title: "Daemonset",
      },
      {
        id: "deployment",
        title: "Daemonset",
      },
      {
        id: "replicaset",
        title: "Replicaset",
      },
      {
        id: "statefulset",
        title: "Statefulset",
      },
      {
        id: "pod",
        title: "Pod",
      },
      {
        id: "pv",
        title: "PVCs",
      },
    ],
    active: true,
  },
  {
    title: "Manager",
    id: "manager",
    icon: <AppstoreAddOutlined />,
    active: true,
  },
];

const SiderMenu = styled(Layout.Sider)`
  position: relative;
  padding-top: 15px;
  padding-bottom: 200px;
  height: calc(100vh - 55px);

  .ant-menu-root > .ant-menu-item,
  .ant-menu-submenu-title {
    font-weight: bold;
  }
  .ant-menu-submenu li {
    padding-left: 53px !important; /* antd doesn't allow padding value via props etc. */
  }
  .ant-menu-submenu-arrow {
    display: none;
  }
`;

function SideMenu() {
  const [defaultKey, setDefaultKey] = useState("detail");
  const history = useHistory();

  useEffect(() => {
    const path = history.location.pathname.split("/");
    setDefaultKey(path[path.length - 1]);
  }, [history.location.pathname]);

  return (
    <SiderMenu>
      <Menu
        theme="dark"
        selectedKeys={[defaultKey]}
        mode="inline"
        openKeys={[""]}
      >
        {menuList.map((menu) =>
          menu.sub ? (
            <Menu.SubMenu key={menu.id} icon={menu.icon} title={menu.title}>
              {menu.sub.map((sub) => (
                <Menu.Item key={sub.id}>
                  <Link to={`/${menu.id}/${sub.id}?}`}>{sub.title}</Link>
                </Menu.Item>
              ))}
            </Menu.SubMenu>
          ) : (
            <Menu.Item key={menu.id} icon={menu.icon}>
              {menu.active ? (
                <Link
                  to={`/${menu.id}${menu?.detail ? `/${menu?.detail}` : ""}`}
                >
                  {menu.title}
                </Link>
              ) : (
                <div>{menu.title}</div>
              )}
            </Menu.Item>
          )
        )}
      </Menu>
    </SiderMenu>
  );
}

export default SideMenu;
