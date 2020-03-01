module Global = {
  [@react.component] [@bs.module "@ant-design/icons"]
  external make: (~style: ReactDOMRe.Style.t=?) => React.element =
    "GlobalOutlined";
};

module Account = {
  [@react.component] [@bs.module "@ant-design/icons"]
  external make: (~style: ReactDOMRe.Style.t=?) => React.element =
    "UserOutlined";
};

module Calendar = {
  [@react.component] [@bs.module "@ant-design/icons"]
  external make: (~style: ReactDOMRe.Style.t=?) => React.element =
    "CalendarOutlined";
};

module Chart = {
  [@react.component] [@bs.module "@ant-design/icons"]
  external make: (~style: ReactDOMRe.Style.t=?) => React.element =
    "AreaChartOutlined";
};