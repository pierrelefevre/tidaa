arr = {
    a: 0,
    b: 0,
    c: 0,
    d: 0,
    e: 0,
    f: 0,
    g: 0,
    dp: 0
}

function toggle(num){
    if(parseInt(num) == 0){
        return 1;
    }else{
        return 0;
    }
}

$(window).on("load", function(){
    $(".segment").on("click", function(){
        switch ($(this).text()) {
            case "a":
                arr.a = toggle(arr.a);
                $(".a").toggleClass("selected");
                break;

            case "b":
                arr.b = toggle(arr.b);  
                $(".b").toggleClass("selected");              
                break;

            case "c":
                arr.c = toggle(arr.c);   
                $(".c").toggleClass("selected");         
                break;
                
            case "d":
                arr.d = toggle(arr.d);  
                $(".d").toggleClass("selected");              
                break;

            case "e":
                arr.e = toggle(arr.e);  
                $(".e").toggleClass("selected");              
                break;

            case "f":
                arr.f = toggle(arr.f);  
                $(".f").toggleClass("selected");              
                break;

            case "g":
                arr.g = toggle(arr.g);   
                $(".g").toggleClass("selected");             
                break;

            case "dp":
                arr.dp = toggle(arr.dp); 
                $(".dp").toggleClass("selected");               
                break;
        }

        $(".result").text( "" + arr.dp + arr.g + arr.f + arr.e + arr.d + arr.c + arr.b + arr.a );
    });
});
