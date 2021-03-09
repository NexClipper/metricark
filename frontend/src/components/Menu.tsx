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
        id: "daemonsets",
        title: "Daemonsets",
      },
      {
        id: "deployments",
        title: "Deployments",
      },
      {
        id: "replicasets",
        title: "Replicasets",
      },
      {
        id: "statefulsets",
        title: "Statefulsets",
      },
      {
        id: "pods",
        title: "Pods",
      },
      {
        id: "nodes",
        title: "Nodes",
      },
      {
        id: "services",
        title: "Services",
      },
    ],
    active: true,
  },
  // {
  //   title: "Manager",
  //   id: "manager",
  //   icon: <AppstoreAddOutlined />,
  //   active: true,
  // },
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
  const [defaultKey, setDefaultKey] = useState("");
  const history = useHistory();

  useEffect(() => {
    const onChange = () => {
      const path = history.location.pathname.split("/");
      path.shift();
      const k = path[Math.min(1, path.length - 1)];
      setDefaultKey(k);
    };
    history.listen(onChange);
    onChange();
  }, [history]);

  return (
    <SiderMenu>
      <Menu
        theme="dark"
        selectedKeys={[defaultKey]}
        mode="inline"
        defaultOpenKeys={["kubernetes"]}
      >
        {menuList.map((menu) =>
          menu.sub ? (
            <Menu.SubMenu key={menu.id} icon={menu.icon} title={menu.title}>
              <Menu.Item key={menu.id}>
                <Link to={`/${menu.id}`}>Dashboard</Link>
              </Menu.Item>
              {menu.sub.map((sub) => (
                <Menu.Item key={sub.id}>
                  <Link to={`/${menu.id}/${sub.id}`}>{sub.title}</Link>
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
