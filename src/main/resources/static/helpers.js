function search(){
  search_str = $('.search_box').val()
  console.log(search_str)
  $.ajax({
       url: "http://localhost:7000/search?serch_term="+search_str
   }).then(function(grocery_list) {
      $('.out_contaner')[0].innerHTML=""
      grocery_list.forEach(function(grocery_item) {
        $('.out_contaner').append('<div class="container_grocery_list"> </div>');
        grocery_item.split(',').forEach(function(grocery_part) {
          $('.container_grocery_list:last').append(
            '<div class="item_grocery_list">' +
            grocery_part + ',  </div>'
          );
        });
      });
   });
}
