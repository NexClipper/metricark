import React from "react";
import { Layout as AntLayout } from "antd";

import Menu from "./Menu";

const { Footer, Sider, Content } = AntLayout;

const Layout: React.FC = ({ children }) => {
  return (
    <AntLayout style={{ minHeight: `100vh`, minWidth: "1280px" }}>
      <AntLayout>
        <Sider>
          <Menu />
        </Sider>
        <Content>{children}</Content>
      </AntLayout>
      <Footer>© 2021 Nexcloud</Footer>
    </AntLayout>
  );
};

export default Layout;
